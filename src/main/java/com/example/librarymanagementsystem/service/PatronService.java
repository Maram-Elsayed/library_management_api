package com.example.librarymanagementsystem.service;

import com.example.librarymanagementsystem.dto.PatronCreateInfo;
import com.example.librarymanagementsystem.dto.PatronDto;
import com.example.librarymanagementsystem.exception.BaseValidationException;
import com.example.librarymanagementsystem.exception.ErrorMessage;
import com.example.librarymanagementsystem.persistence.entity.Patron;
import com.example.librarymanagementsystem.persistence.repository.BorrowRecordRepository;
import com.example.librarymanagementsystem.persistence.repository.PatronRepository;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PatronService extends ValidationService {
    @Autowired
    private PatronRepository patronRepository;
    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Transactional(readOnly = true)
    @Cacheable(value = "patrons", key = "#id")
    public PatronDto findPatron(Long id) throws BaseValidationException {
        Patron patron = getPatronById(id);
        return mapToPatronDto(patron);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "patrons", key = "'allPatrons'")
    public List<PatronDto> findAllPatrons() {
        List<Patron> patrons = patronRepository.findAll();
        return patrons.stream().map(this::mapToPatronDto).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "patrons", key = "'allPatrons'")
    public PatronDto createPatron(PatronCreateInfo patronCreateInfo) throws BaseValidationException {
        if(!patronRepository.findAllByPhone(patronCreateInfo.getPhone()).isEmpty()) {
            throw new BaseValidationException(ErrorMessage.PHONE_NOT_UNIQUE);
        }
        return createOrUpdatePatron(patronCreateInfo, null);
    }

    @Transactional
    @CacheEvict(value = "patrons", key = "'allPatrons'")
    @CachePut(value = "patrons", key = "#id")
    public PatronDto updatePatron(Long id, PatronCreateInfo patronCreateInfo) throws BaseValidationException {
        Patron patron = getPatronById(id);
        if(patronCreateInfo.getPhone() != null && patronRepository.findByPhoneAndIdNot(patronCreateInfo.getPhone(), patron.getId()).isPresent()) {
            throw new BaseValidationException(ErrorMessage.PHONE_NOT_UNIQUE);
        }
        return createOrUpdatePatron(patronCreateInfo, patron.getId());
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "patrons", key = "'allPatrons'"),
            @CacheEvict(value = "patrons", key = "#id")
    })
    public void deletePatron(Long id) throws BaseValidationException {
        Patron patron = getPatronById(id);
        if(!borrowRecordRepository.findAllByPatronIdAndReturnDateIsNull(patron.getId()).isEmpty()) {
            throw new BaseValidationException(ErrorMessage.CAN_NOT_DELETE_PATRON_WITH_BORROWED_BOOK);
        }
        patronRepository.delete(patron);
    }

    @Transactional(readOnly = true)
    public Patron getPatronById(Long id) throws BaseValidationException {
        return patronRepository.findById(id)
                .orElseThrow(() -> new BaseValidationException(ErrorMessage.ENTITY_NOT_FOUND, Patron.class.getSimpleName()));
    }

    public PatronDto mapToPatronDto(Patron patron) {
        return PatronDto.builder().id(patron.getId())
                .name(patron.getName())
                .phone(patron.getPhone())
                .build();
    }

    @Transactional
    private PatronDto createOrUpdatePatron(PatronCreateInfo patronCreateInfo, Long id) throws BaseValidationException {
        Patron patron = Patron.builder().id(id)
                .name(patronCreateInfo.getName())
                .phone(patronCreateInfo.getPhone())
                .build();
        List<ConstraintViolation<?>> violations = validateNotNull(patron);
        if(!violations.isEmpty()) {
            throw new BaseValidationException(ErrorMessage.VALIDATION_ERROR, violations);
        }
        patron = patronRepository.save(patron);

        return mapToPatronDto(patron);
    }
}
