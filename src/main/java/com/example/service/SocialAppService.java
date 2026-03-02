package com.example.service;

import com.example.enums.Role;
import com.example.exception.UserNotFoundException;
import com.example.model.User;
import com.example.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@AllArgsConstructor
public class SocialAppService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepo userRepository;

    private static final Logger logger = LoggerFactory.getLogger(SocialAppService.class);

        @Override
        @Transactional
        public OAuth2User loadUser(OAuth2UserRequest userRequest) {
            OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            String login = oAuth2User.getAttribute("login");
            User user = userRepository.findByLogin(login).orElseGet(()-> {
                User newUser =new User();
                newUser.setLogin(login);
                newUser.setEmail(oAuth2User.getAttribute("email"));
                newUser.setName(oAuth2User.getAttribute("name"));
                newUser.setId(oAuth2User.getAttribute("id"));
                logger.info("User {} has been loaded", login);
                newUser.setRole(Role.USER);
                return newUser;
            });

            user.setName(oAuth2User.getAttribute("name"));
            user.setEmail(oAuth2User.getAttribute("email"));

            logger.info("User {} has been login", login);
            userRepository.save(user);
            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                    oAuth2User.getAttributes(),
                    "login"
            );
        }

    public void giveAdminRole(Long id) {
            User user = userRepository.findById(id).orElseThrow(()->
                    new UserNotFoundException("User not found with id " + id));
            user.setRole(Role.ADMIN);
            userRepository.save(user);
    }
}
