package co.mykaredemo.annotation;

import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.CLASS)
@Mappings({
        @Mapping(
                target = "id",
                ignore = true),
        @Mapping(
                target = "createdOn",
                ignore = true),
        @Mapping(target = "updatedOn",
                ignore = true),
        @Mapping(target = "version",
                ignore = true)

})
public @interface IgnoreBaseAudit {
}
