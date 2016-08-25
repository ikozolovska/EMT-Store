package org.axonframework.samples.trader.webui.home;

import org.axonframework.samples.trader.webui.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by DELL-PC on 6/13/2016.
 */
@Controller
@RequestMapping("/")
public class HomePageController {

    @Autowired
    StoreService storeService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(Model model) { return "home/index"; }
}