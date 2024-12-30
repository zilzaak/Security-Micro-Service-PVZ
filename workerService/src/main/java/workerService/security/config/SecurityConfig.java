package workerService.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import workerService.security.dynamicLoad.DynamicAuthorizationFilter;
import workerService.security.service.DynamicPermissionService;

import java.util.Map;



@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private    CustomUserDetailsService customUserDetailsService;
    //sdfgsjk
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Autowired
    private DynamicPermissionService dynamicPermissionService; // create username password and related roles object type  beans
    //into the application context
    
    

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    } 
    
    
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorize -> {
                    Map<String, String> permissions = dynamicPermissionService.getPermissions();
                    permissions.forEach((key, value) -> {
                        if ("USER_REG".equals(value)) {
                            authorize.antMatchers(key).permitAll();
                        } else {
                            String[] roleArray = value.split(",");
                            authorize.antMatchers(key).hasAnyAuthority(roleArray);
                        }
                    });
                    authorize.anyRequest().authenticated();
                })
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add JWT filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(dynamicAuthorizationFilter(), JwtRequestFilter.class);

    }

    @Bean
    public DynamicAuthorizationFilter dynamicAuthorizationFilter() {
        return new DynamicAuthorizationFilter();
    }

    @SuppressWarnings("deprecation")
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}

