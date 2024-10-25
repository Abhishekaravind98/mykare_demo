package co.mykaredemo.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class AbstractBasicView extends AbstractView {
    private static final long serialVersionUID = 1L;
}
