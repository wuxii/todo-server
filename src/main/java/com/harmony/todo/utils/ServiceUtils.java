package com.harmony.todo.utils;

import com.harmony.todo.core.repository.QueryableRepository;
import com.harmony.todo.core.service.ServiceSupport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class ServiceUtils {

    public static <T> QueryableRepository<T> findBestMatchRepository(ServiceSupport<T> service) {
        Class<? extends ServiceSupport> clazz = service.getClass();
        Class<T> domainClass = findClassDomainClass(clazz);
        return findRepository(service, domainPredicate(domainClass));
    }

    private static <T> QueryableRepository<T> findRepository(ServiceSupport service, Predicate<Field> predicate) {
        Field[] fields = service.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (QueryableRepository.class.isAssignableFrom(field.getType()) && predicate.test(field)) {
                try {
                    ReflectionUtils.makeAccessible(field);
                    return (QueryableRepository<T>) ReflectionUtils.getField(field, service);
                } catch (Exception e) {
                    log.error("unable find service support repository", e);
                }
            }
        }
        throw new IllegalStateException("not matched repository found " + service);
    }

    private static Predicate<Field> domainPredicate(Class<?> domainClass) {
        return (field) -> findClassDomainClass(field.getType()).isAssignableFrom(domainClass);
    }

    private static <T> Class<T> findClassDomainClass(Class<?> clazz) {
        List<Type> types = getClassGenericTypes(clazz);
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                for (Type argType : actualTypeArguments) {
                    if (argType instanceof Class) {
                        return (Class<T>) argType;
                    }
                }
            }
        }
        throw new IllegalStateException("unable find domain class at " + clazz);
    }

    private static List<Type> getClassGenericTypes(Class<?> clazz) {
        List<Type> types = new ArrayList<>();
        Collections.addAll(types, clazz.getGenericInterfaces());
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass != null) {
            types.add(genericSuperclass);
        }
        return types;
    }

}
