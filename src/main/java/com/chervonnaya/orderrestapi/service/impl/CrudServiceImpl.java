package com.chervonnaya.orderrestapi.service.impl;

import com.chervonnaya.orderrestapi.dto.BaseDTO;
import com.chervonnaya.orderrestapi.exception.EntityNotFoundException;
import com.chervonnaya.orderrestapi.exception.SaveEntityException;
import com.chervonnaya.orderrestapi.exception.UnableToDeleteException;
import com.chervonnaya.orderrestapi.model.BaseEntity;
import com.chervonnaya.orderrestapi.repository.IRepository;
import com.chervonnaya.orderrestapi.service.CrudService;
import com.chervonnaya.orderrestapi.service.mappers.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
public class CrudServiceImpl<E extends BaseEntity, D extends BaseDTO, R extends IRepository<E>> implements CrudService<E, D> {


    final R repository;
    final Class<E> genericType;
    final BaseMapper<E, D> mapper;

    @Autowired
    public CrudServiceImpl(R repository, Class<E> genericType, BaseMapper<E, D> mapper) {
        this.repository = repository;
        this.genericType = genericType;
        this.mapper = mapper;
    }

    @Override
    public E getById(Long id) {
        Optional<E> entity = repository.findById(id);
        if(entity.isPresent()) {
            log.info(String.format("Found %s with id %s",
                this.genericType.getSimpleName().toLowerCase(), id));
            return entity.get();
        }
        log.error(String.format("Unable to get %s with id %s",
            this.genericType.getSimpleName().toLowerCase(), id));
        throw new EntityNotFoundException(this.genericType.getSimpleName(), id);
    }

    @Transactional
    @Override
    public E save(D d) {
        try {
            E entity = repository.save(mapper.map(d));
            log.info(String.format("Saved %s, id: %d",
                this.genericType.getSimpleName().toLowerCase(), entity.getId()));
            return entity;
        } catch (Exception ex) {
            log.error(String.format("Unable to save %s", this.genericType.getSimpleName().toLowerCase()));
            throw new SaveEntityException(this.genericType.getSimpleName(), ex.getMessage());
        }
    }

    @Transactional
    @Override
    public E update(Long id, D d) {
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException(this.genericType.getSimpleName(), id));
        try {
            d.setId(id);
            E entity = repository.save(mapper.map(d));
            log.info(String.format("Updated %s, id: %d",
                this.genericType.getSimpleName().toLowerCase(), entity.getId()));
            return entity;
        } catch (Exception ex) {
            log.error(String.format("Unable to update %s", this.genericType.getSimpleName().toLowerCase()), id);
            throw new SaveEntityException(this.genericType.getSimpleName(), id, ex.getMessage());
        }

    }

    @Transactional
    @Override
    public Long delete(Long id) {
        try {
            repository.deleteById(id);
            log.info(String.format("%s with id %d is deleted",
                this.genericType.getSimpleName(), id));
            return id;
        } catch (Exception ex) {
            log.error(String.format("Unable to delete %s with id %d. Error message: %s",
                this.genericType.getSimpleName().toLowerCase(), id, ex.getMessage()));
            throw new UnableToDeleteException(this.genericType.getSimpleName(), id);
        }
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        Page<E> all = repository.findAll(pageable);
        log.info(String.format("Created page of %ss", this.genericType.getSimpleName().toLowerCase()));
        return all;
    }

    public List<E> findAll() {
        return repository.findAll();
    }
}
