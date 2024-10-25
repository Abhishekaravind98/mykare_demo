package co.mykaredemo.modules.user.controllers;

import co.mykaredemo.dtos.PaginatedList;
import co.mykaredemo.dtos.ResponseModel;
import co.mykaredemo.exceptions.ControllerException;
import co.mykaredemo.modules.user.dtos.UserCreateRequest;
import co.mykaredemo.modules.user.dtos.UserPasswordUpdateRequest;
import co.mykaredemo.modules.user.dtos.UserUpdateRequest;
import co.mykaredemo.modules.user.dtos.UserView;
import co.mykaredemo.modules.user.misc.UserFilter;
import co.mykaredemo.modules.user.ports.api.UserServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserServicePort userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseModel<UserView> create(@RequestBody @Valid UserCreateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        UserView userView = userService.create(request);
        return ResponseModel.of(userView);
    }

    @GetMapping("{id}")
    public ResponseModel<UserView> get(@PathVariable("id") Long id) {
        UserView userView = userService.get(id);
        return ResponseModel.of(userView);
    }

    @PostMapping("search")
    public ResponseModel<PaginatedList<UserView>> search(@RequestBody UserFilter filter) {
        PaginatedList<UserView> list = userService.getAllPaginated(filter);
        return ResponseModel.of(list);
    }

    @GetMapping()
    public ResponseModel<List<UserView>> get(@RequestBody UserFilter filter) {
        List<UserView> list = userService.getAll(filter);
        return ResponseModel.of(list);
    }


    @PutMapping("{id}")
    public ResponseModel<UserView> update(@PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequest request,
                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        UserView userView = userService.update(id, request);
        return ResponseModel.of(userView);
    }

    @DeleteMapping("{id}")
    public ResponseModel<Long> delete(@PathVariable("id") Long id) {
        UserView userView = userService.delete(id);
        return ResponseModel.of(userView.getId());
    }

    @PatchMapping("reset-password")
    public ResponseModel<Long> resetPassword(@RequestBody @Valid UserPasswordUpdateRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Request contains validation errors");
            throw new ControllerException(bindingResult);
        }
        UserView userView = userService.resetPassword(request);
        return ResponseModel.of(userView.getId());
    }

    @PatchMapping("{id}/block")
    public ResponseModel<Long> blockUser(@PathVariable("id") Long id) {
        UserView userView = userService.blockUser(id);
        return ResponseModel.of(userView.getId());
    }

    @PatchMapping("{id}/unblock")
    public ResponseModel<Long> unBlockUser(@PathVariable("id") Long id) {
        UserView userView = userService.unBlockUser(id);
        return ResponseModel.of(userView.getId());
    }
}
