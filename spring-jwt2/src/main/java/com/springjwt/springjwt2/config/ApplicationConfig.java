package com.springjwt.springjwt2.config;

import com.springjwt.springjwt2.dao.UserRepository;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.lang.model.SourceVersion;
import java.util.Locale;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {


    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){

        return username -> userRepository.findByEmail(username) ;

    }

    @Bean   // this class is dao which responsible for fetch all userdetails form databse and encode the passowod
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());

        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean  // this method to mange the authentication
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
     return    configuration.getAuthenticationManager();
    }

    // to create swagger api
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }

}
