package com.harmony.todo.core.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface QueryableRepository<T> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {
}
