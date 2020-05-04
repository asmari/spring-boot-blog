package com.example.demo.controller;

import com.example.demo.config.CustomPageConfiguration;
import com.example.demo.config.CustomPageableConfiguration;
import com.example.demo.config.ResponseBaseConfiguration;
import com.example.demo.dto.request.AuthorRequestDto;
import com.example.demo.dto.response.AuthorResponseDTo;
import com.example.demo.dto.response.OauthResponseDto;
import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;
import com.example.demo.util.PageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/authors")
@Slf4j
public class AuthorController{
    @Autowired
    private AuthorService authorService;
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ClientDetailsService clientDetailsStore;

    @Autowired
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping()
    public ResponseBaseConfiguration<CustomPageConfiguration<AuthorResponseDTo>> listAuthors(
            CustomPageableConfiguration pageable, @RequestParam(required = false) String search, HttpServletRequest request
    ) {
        Page<AuthorResponseDTo> author;
        log.info(String.valueOf(search));
        if (search == null || search.isEmpty()) {
            author = authorService.findAll(CustomPageableConfiguration.convertToPageable(pageable));
        } else {
            author = authorService.findByNameContaining(CustomPageableConfiguration.convertToPageable(pageable),search);
        }
        log.info(String.valueOf(author));

        PageConverter<AuthorResponseDTo> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

        String searchParam = "";

        if(search != null){
            searchParam += "&search="+search;
        }

        CustomPageConfiguration<AuthorResponseDTo> response = converter.convert(author, url, searchParam);

        return ResponseBaseConfiguration.ok(response);
    }

    @PostMapping()
    public ResponseBaseConfiguration createAuthor(@RequestBody @Valid AuthorRequestDto request) {

        authorService.save(request);
        return ResponseBaseConfiguration.created();
    }

    //Normal Login
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ResponseEntity<OauthResponseDto> login(@RequestBody HashMap<String, String> params) throws Exception
    {
        OauthResponseDto response = new OauthResponseDto();
        Author checkUser =  authorService.findByUsername(params.get("username"));

        if (checkUser != null)
        {
            try {
                OAuth2AccessToken token = this.getToken(params);

                response.setStatus(true);
                response.setCode("200");
                response.setMessage("success");
                response.setData(token);

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (Exception exception) {

                response.setStatus(false);
                response.setCode("500");
                response.setMessage(exception.getMessage());
            }
        } else {
            throw new Exception();
        }


        return new ResponseEntity<OauthResponseDto>(response, HttpStatus.UNAUTHORIZED);
    }

    private OAuth2AccessToken getToken(HashMap<String, String> params) throws HttpRequestMethodNotSupportedException {
        if (params.get("username") == null ) {
            throw new UsernameNotFoundException("username not found");
        }

        if (params.get("password") == null) {
            throw new UsernameNotFoundException("password not found");
        }

        if (params.get("client_id") == null) {
            throw new UsernameNotFoundException("client_id not found");
        }

        if (params.get("client_secret") == null) {
            throw new UsernameNotFoundException("client_secret not found");
        }

        DefaultOAuth2RequestFactory defaultOAuth2RequestFactory = new DefaultOAuth2RequestFactory(clientDetailsStore);

        AuthorizationRequest authorizationRequest = defaultOAuth2RequestFactory.createAuthorizationRequest(params);
        authorizationRequest.setApproved(true);

        OAuth2Request oauth2Request = defaultOAuth2RequestFactory.createOAuth2Request(authorizationRequest);

        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                params.get("username"), params.get("password"));
        Authentication authentication = authenticationManager.authenticate(loginToken);

        OAuth2Authentication authenticationRequest = new OAuth2Authentication(oauth2Request, authentication);
        authenticationRequest.setAuthenticated(true);

        OAuth2AccessToken token = tokenServices().createAccessToken(authenticationRequest);


        return token;
    }

    @Autowired
    public AuthorizationServerTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setAccessTokenValiditySeconds(-1);

        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
}