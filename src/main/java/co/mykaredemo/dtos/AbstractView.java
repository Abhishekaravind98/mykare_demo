package co.mykaredemo.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class AbstractView implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
}
