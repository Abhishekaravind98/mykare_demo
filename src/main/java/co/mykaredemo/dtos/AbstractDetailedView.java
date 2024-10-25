package co.mykaredemo.dtos;


import co.mykaredemo.enums.EntityStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractDetailedView extends AbstractView {

    private Date createdOn;

    private Date updatedOn;

    private EntityStatus status;
}