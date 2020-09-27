package rest.crud.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import rest.crud.app.config.handler.LoginSuccessHandler;
import rest.crud.app.service.UserDetailsServiceAdded;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceAdded userDetailsServiceAdded;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceAdded userDetailsServiceAdded) {
        this.userDetailsServiceAdded = userDetailsServiceAdded;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceAdded).passwordEncoder(passwordEncoder());
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                //.loginPage("/login") // указываем страницу с формой логина
                .successHandler(new LoginSuccessHandler()) //указываем логику обработки при логине
                //.loginProcessingUrl("/login") // указываем поле action из формы логина
                .permitAll(); // даем доступ к форме логина всем

        http.logout().permitAll() // разрешаем делать логаут всем
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // указываем URL логаута
                .logoutSuccessUrl("/login") // указываем URL при удачном логауте
                .and().csrf().disable(); //выключаем кроссдоменную секьюрность (на этапе обучения неважна)

        http.authorizeRequests()//тут мы предоставляем разрешения для следующих url
                .antMatchers("/save*").permitAll()
                .antMatchers("/login*").anonymous()// access denied if already logged-in , "/registration*"
                .antMatchers("/user*").hasAnyRole("USER", "ADMIN")
                .antMatchers("/index*", "/admin/**").access("hasRole('ADMIN')")
                .anyRequest().authenticated();
    }
/*

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }*/

    @Bean
    public DaoAuthenticationProvider authProvider() throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceAdded);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
