package co.mykaredemo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"list"})
public class PaginatedList<T extends AbstractView> implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<T> list;

    private Integer recordsPerPage;

    private long totalRecords;

    private Integer page;

    private Integer totalPages;

    public PaginatedList(Page<T> ePage) {
        this.list = ePage.getContent();
        this.recordsPerPage = ePage.getPageable().getPageSize();
        this.totalRecords = ePage.getTotalElements();
        this.page = ePage.getNumber();
        this.totalPages = ePage.getTotalPages();
    }

    public PaginatedList(List<T> data, int currentPage, int rowsPerPage, int totalRowCount) {
        setList(data);
        if (getList().size() > totalRowCount) throw new RuntimeException("Total row count not set.");
        this.page = currentPage;
        this.recordsPerPage = rowsPerPage;
        this.totalRecords = totalRowCount;
        this.totalPages = (int) Math.ceil((double) totalRowCount / rowsPerPage);
    }

    //    public PaginatedList(List<T> data, BaseFilter filter) {
//        this(data, filter.getCurrentPage(), filter.getRowsPerPage(), filter.getTotalRows());
//    }
    public List<T> getList() {
        return list == null ? new ArrayList<>() : list;
    }

    public PaginatedList<T> setList(List<T> data) {
        this.list = data;
        this.page = 1;
        this.recordsPerPage = getList().size();
        this.totalRecords = getList().size();
        return this;
    }
}
