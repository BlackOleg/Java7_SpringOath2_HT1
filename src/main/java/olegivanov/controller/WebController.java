package olegivanov.controller;


import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {
    @GetMapping("/")
    public String hello() {
        return "Hello everybody! This is free access address!";
    }

    @GetMapping("/hi")
    public String hello2() {
        return "You got it! This is free access endpoint!";
    }

    @Secured({"ROLE_READ"})
    @GetMapping("/read-zone")
    public String zone1() {
        return "This is read zone";
    }

    @RolesAllowed({"ROLE_WRITE"})
    @GetMapping("/write-zone")
    public String zone2() {
        return "This is write zone ";
    }
    @PreAuthorize("hasAnyRole('WRITE', 'DELETE')")
    @GetMapping("/zone3")
    public String zone3() {
        return "This is just for WRITE & DELETE roles ";
    }
    @PreAuthorize("hasAnyRole('READ','WRITE','DELETE')")
    @GetMapping("/zone4")
    @ResponseBody
    public String zone4(@RequestParam("username") String username) throws Exception {
        var authUser=SecurityContextHolder.getContext().getAuthentication().getName();
        if (!authUser.equals(username)) {
            throw new Exception("Person is not eligible for access");
        }
        return "This is zone just for special username like "+username+" where authUser=  "+authUser;
    }

}
