package com.kitt51.PetVax.Security;

import com.kitt51.PetVax.Pet.Query.Domain.PetViewRepository;
import com.kitt51.PetVax.Pet.Query.Service.PetQueryService;
import com.kitt51.PetVax.User.Model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.function.Supplier;

public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {


    private final PetQueryService queryService;

    public UserSecurity(PetQueryService queryService) {
        this.queryService = queryService;
    }


    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext ctx) {
        Long userId = Long.parseLong(ctx.getVariables().get("userId"));
        Authentication authentication = (Authentication) authenticationSupplier.get();
        return new AuthorizationDecision(hasUserId(authentication, userId));

    }

    public boolean hasUserId(Authentication authentication, Long userId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        var principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser securityUser) {

            return securityUser.getId().equals(userId);
        }

        return false;
    }

    public boolean hasAccessToPet(Authentication authentication, Long petId){
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof SecurityUser securityUser) {
            Long userId = securityUser.getId();

            return queryService.isPetOwnedByUser(petId, userId);
        }
        return false;
    }



}
