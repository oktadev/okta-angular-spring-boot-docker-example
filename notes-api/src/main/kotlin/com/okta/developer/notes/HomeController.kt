package com.okta.developer.notes

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping("/")
    fun hello(@AuthenticationPrincipal user: OidcUser): String {
        return "Hello, ${user.fullName}"
    }
}
