package co.mykaredemo.modules.user.dtos;

import co.mykaredemo.dtos.AbstractBasicView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserBasicView extends AbstractBasicView {
    private String name;
    private String phoneNumber;
    private String emailId;
}
