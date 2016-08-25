/*
 * Copyright (c) 2010-2012. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.samples.trader.webui.security;

import org.axonframework.samples.trader.query.order.OrderEntry;
import org.axonframework.samples.trader.query.users.repositories.UserQueryRepository;
import org.axonframework.samples.trader.webui.services.StoreCommandService;
import org.axonframework.samples.trader.webui.services.StoreService;
import org.axonframework.samples.trader.webui.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Jettro Coenradie
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private UserQueryRepository userRepository;
    @Autowired
    StoreService storeService;
    @Autowired
    StoreCommandService storeCommandService;

    @Autowired
    public UserController(UserQueryRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showUsers(Model model) {
        model.addAttribute("items", userRepository.findAll());
        return "user/list";
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.GET)
    public String addNewUserForm(Model model){
        return "user/new";
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    public String addNewUser(@RequestParam("first_name")String firstName,
                             @RequestParam("last_name")String lastName,
                             @RequestParam("display_name")String userName,
                             @RequestParam("email")String email,
                             @RequestParam("phone")String phone,
                             @RequestParam("password")String password,
                             @RequestParam("password_confirmation")String passwordConfirm) {


        if(!storeService.userNameExists(userName))
            storeCommandService.createUser(firstName, lastName, userName, email, phone, password);
        return "redirect:/";
    }

    @RequestMapping(value = "/{identifier}", method = RequestMethod.GET)
    public String detail(@PathVariable("identifier") String userIdentifier, Model model) {
        model.addAttribute("item", userRepository.findByIdentifier(userIdentifier));
        return "user/detail";
    }

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String orders(Model model) {

        String userId = SecurityUtil.obtainLoggedinUserIdentifier();
        List<OrderEntry> orderEntryList = storeService.getOrdersForUser(userId);
        model.addAttribute("orders", orderEntryList);
        return "user/orders";
    }
}