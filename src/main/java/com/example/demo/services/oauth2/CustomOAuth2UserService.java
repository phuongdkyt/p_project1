package com.example.demo.services.oauth2;


import com.example.demo.exception.OAuth2AuthenticationProcessingException;
import com.example.demo.models.dto.AuthProvider;
import com.example.demo.models.dto.Role;
import com.example.demo.models.dto.UserStatus;
import com.example.demo.models.entity.Permission;
import com.example.demo.models.entity.User;
import com.example.demo.models.entity.UserPermission;
import com.example.demo.repositories.PermissionRepo;
import com.example.demo.repositories.UserPerRepo;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.UserDetailsImpl;
import com.example.demo.services.oauth2.user.OAuth2UserInfo;
import com.example.demo.services.oauth2.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private UserPerRepo userPerRepo;

    @Autowired
    private PermissionRepo permissionRepo;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if (ObjectUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserDetailsImpl.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        User user = new User();

        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setFirstName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImage(oAuth2UserInfo.getImageUrl());
        user.setUsername(oAuth2UserInfo.getId());
        user.setStatus(UserStatus.ACTIVE.name());
        Permission role = permissionRepo.findByName(Role.CUSTOMER.name()).get();
        userPerRepo.save(UserPermission.builder().perId(role.getId()).userId(user.getId()).build());

        return userRepository.save(user);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFirstName(oAuth2UserInfo.getName());
        existingUser.setImage(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
