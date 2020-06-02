package com.okta.developer.notes

import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class LogoutController(val clientRegistrationRepository: ClientRegistrationRepository) {

    val registration: ClientRegistration = clientRegistrationRepository.findByRegistrationId("okta");

    @PostMapping("/api/logout")
    fun logout(request: HttpServletRequest,
               @AuthenticationPrincipal(expression = "idToken") idToken: OidcIdToken): ResponseEntity<*> {
        val logoutUrl = this.registration.providerDetails.configurationMetadata["end_session_endpoint"]
        val logoutDetails: MutableMap<String, String> = HashMap()
        logoutDetails["logoutUrl"] = logoutUrl.toString()
        logoutDetails["idToken"] = idToken.tokenValue
        request.session.invalidate()
        return ResponseEntity.ok().body<Map<String, String>>(logoutDetails)
    }
}
