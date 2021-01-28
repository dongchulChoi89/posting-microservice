package com.choi.springboot.config.auth;

import com.choi.springboot.config.auth.dto.OAuthAttributes;
import com.choi.springboot.config.auth.dto.SessionUser;
import com.choi.springboot.domain.user.User;
import com.choi.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

// class which implements UserService interface when you want to use the user's information from resource servers(google ...) after OAuth2 login is done successfully
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // to identify current using OAuth2 service(google/naver ...)
        String userNameAttributeName = userRequest // primary key when doing OAuth2 login
                                        .getClientRegistration().getProviderDetails()
                                        .getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes // OAuthAttributes class is DTO and contains attributes of OAuth2User from OAuth2UserService
                                        .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes); // save in the User db // when google account is updated, we can also update by using saveOrUpdate

        httpSession.setAttribute("user", new SessionUser(user)); // set session // SessionUser is dto to save user info in session // User class is entity class so we do not use User class to implement serialization functionality

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture())) // update
                .orElse(attributes.toEntity()); // save

        return userRepository.save(user);
    }
}
