package org.axonframework.samples.trader.webui.rest;

import com.fasterxml.jackson.annotation.JsonView;
import org.axonframework.samples.trader.query.JsonViews;
import org.axonframework.samples.trader.query.users.UserEntry;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.axonframework.samples.trader.webui.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/user")
public class UserRestController {

    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;

    @RequestMapping(method = RequestMethod.GET)
    @JsonView(JsonViews.Public.class)
    public UserEntry getAuthenticatedUserA() {
        if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            return new UserEntry();
        } else {
            return storeService.findUser(SecurityUtil.obtainLoggedinUserIdentifier());
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @JsonView(JsonViews.Public.class)
    public UserEntry getUser(@PathVariable("userId")String userId) {
        return storeService.findUser(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addNewUser(@RequestParam("first_name")String firstName,
                             @RequestParam("last_name")String lastName,
                             @RequestParam("display_name")String userName,
                             @RequestParam("email")String email,
                             @RequestParam("phone")String phone,
                             @RequestParam("password")String password,
                             @RequestParam("password_confirmation")String passwordConfirm) {

        if(!storeService.userNameExists(userName))
            storeCommandService.createUser(firstName, lastName, userName, email, phone, password);
    }
}