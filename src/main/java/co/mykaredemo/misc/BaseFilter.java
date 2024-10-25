package co.mykaredemo.misc;

import co.mykaredemo.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.io.Serializable;


@Getter
@Setter
public class BaseFilter {


    private String query;
    private EntityStatus[] status;

    private DateFilter dateFilter;
    @PositiveOrZero
    private int page = 0;

    @Min(1)
    @Max(100)
    private int rows = 15;
    private String sortBy;
    private Sort.Direction sortIn = Sort.Direction.DESC;


    public EntityStatus[] getStatus() {
        return status == null || status.length == 0 ? new EntityStatus[]{EntityStatus.ACTIVE} : status;
    }


    @Getter
    @Setter
    public static class DateFilter implements Serializable {
        private String field;

        private Long from;

        private Long to;

        public DateFilter() {
        }

        public DateFilter(String field, Long from, Long to) {
            this.field = field;
            this.from = from;
            this.to = to;
        }

        public DateFilter(Long from, Long to) {
            this.from = from;
            this.to = to;
        }

        @JsonIgnore
        public boolean isValid() {
            return (from != null && to != null && from < to);
        }
    }

    public void setQ(final String q) {
        this.query = q;
    }

    public Sort sort() {
        return Sort.by(new Sort.Order(sortIn, StringUtils.defaultIfBlank(sortBy, "createdOn")));
    }

    public PageRequest filterPageable() {
        return PageRequest.of(getPage(), getRows(), sort());
    }
}
