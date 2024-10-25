package co.mykaredemo.modules.user.dtos;

import co.mykaredemo.dtos.AbstractDetailedView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView extends AbstractDetailedView {
    private String name;
    private String emailId;
    private String phoneNumber;

}
