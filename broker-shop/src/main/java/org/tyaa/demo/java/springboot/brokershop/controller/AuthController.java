package org.tyaa.demo.java.springboot.brokershop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.tyaa.demo.java.springboot.brokershop.models.ResponseModel;
import org.tyaa.demo.java.springboot.brokershop.models.RoleModel;
import org.tyaa.demo.java.springboot.brokershop.models.UserModel;
import org.tyaa.demo.java.springboot.brokershop.services.interfaces.IAuthService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/roles")
    public ResponseEntity<ResponseModel> getRoles () {
        ResponseModel responseModel = authService.getRoles();
        HttpStatus httpStatus;
        if (responseModel.getStatus().equals(ResponseModel.SUCCESS_STATUS)) {
            httpStatus = HttpStatus.OK;
        } else {
            httpStatus = HttpStatus.BAD_GATEWAY;
        }
        return new ResponseEntity<>(responseModel, httpStatus);
    }

    @PostMapping("/roles")
    public ResponseEntity<ResponseModel> createRole (@RequestBody RoleModel roleModel) {
        ResponseModel responseModel = authService.createRole(roleModel);
        HttpStatus httpStatus;
        if (responseModel.getStatus().equals(ResponseModel.SUCCESS_STATUS)) {
            httpStatus = HttpStatus.CREATED;
        } else if (responseModel.getMessage().equals("This name is already taken")) {
            httpStatus = HttpStatus.CONFLICT;
        } else {
            httpStatus = HttpStatus.BAD_GATEWAY;
        }
        return new ResponseEntity<>(responseModel, httpStatus);
    }

    @GetMapping("/admin/roles/{id}/users")
    public ResponseEntity<ResponseModel> getUsersByRole(@PathVariable Long id) {
        ResponseModel responseModel =
                authService.getRoleUsers(id);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getData() != null)
                        ? HttpStatus.OK
                        : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseModel> createUser(@RequestBody UserModel userModel) {
        ResponseModel responseModel =
                authService.createUser(userModel);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getMessage().toLowerCase().contains("created"))
                        ? HttpStatus.CREATED
                        : responseModel.getMessage().contains("name")
                        ? HttpStatus.CONFLICT
                        : HttpStatus.BAD_GATEWAY
        );
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<ResponseModel> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(authService.deleteUser(id), HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/users/{id}/makeadmin")
    public ResponseEntity<ResponseModel> makeUserAdmin(@PathVariable Long id) throws Exception {
        ResponseModel responseModel = authService.makeUserAdmin(id);
        HttpStatus httpStatus;
        if (responseModel.getStatus().equals(ResponseModel.SUCCESS_STATUS)) {
            httpStatus = HttpStatus.OK;
        } else if (responseModel.getMessage().equals(String.format("User #%d Not Found", id))) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            httpStatus = HttpStatus.BAD_GATEWAY;
        }
        return new ResponseEntity<>(responseModel, httpStatus);
    }

    @GetMapping(value = "/user/check")
    // @ResponseBody
    /** @param authentication объект стандартного типа с данными учетной записи
     * пользователя теущего http-сеанса, если ранее произошла успешная аутентификация,
     * получается внедрением зависимости через аргумент метода */
    public ResponseEntity<ResponseModel> checkUser(Authentication authentication) {
        ResponseModel responseModel = authService.check(authentication);
        return new ResponseEntity<>(
                responseModel,
                (responseModel.getData() != null)
                        ? HttpStatus.OK
                        : HttpStatus.UNAUTHORIZED
        );
    }

    @GetMapping("/user/signedout")
    public ResponseEntity<ResponseModel> signedOut(HttpSession httpSession) {
        return new ResponseEntity<>(authService.onSignOut(), HttpStatus.OK);
    }

    @GetMapping("/user/onerror")
    public ResponseEntity<ResponseModel> onError() {
        return new ResponseEntity<>(authService.onError(), HttpStatus.UNAUTHORIZED);
    }
}
