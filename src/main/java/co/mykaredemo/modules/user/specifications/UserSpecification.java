package co.mykaredemo.modules.user.specifications;


import co.mykaredemo.dtos.AbstractTransactionalEntity;
import co.mykaredemo.specifications.AbstractRepositorySpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification<T extends AbstractTransactionalEntity> extends AbstractRepositorySpecification<T> {
    @Override
    protected Specification<T> query(String expression) {
        if (StringUtils.isBlank(expression)) return (root, query, builder) -> builder.conjunction();
        return (root, query, builder) -> builder.or(
                builder.like(root.get("name"), contains(expression)),
                builder.like(root.get("emailId"), contains(expression))
        );
    }
}
