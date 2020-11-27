package org.example.userservice.config;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

@Configuration
public class JwtConfig {

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("public.cert");
        String publicKey;
        try {
//            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            publicKey = getCerToPublicKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }

    public static String getCerToPublicKey() throws Exception {
//        FileInputStream file = new FileInputStream("public.cert");
        Resource resource = new ClassPathResource("public.cert");
        CertificateFactory ft = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) ft.generateCertificate(resource.getInputStream());
        PublicKey publicKey = certificate.getPublicKey();

        String strKey = "-----BEGIN PUBLIC KEY-----\n"
                + Base64.encodeBase64String(publicKey.getEncoded())
                + "\n-----END PUBLIC KEY-----";
        System.out.println(strKey);
        return strKey;
    }


}
