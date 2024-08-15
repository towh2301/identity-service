package com.towh.identity_service.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.towh.identity_service.dto.request.AuthenticationRequest;
import com.towh.identity_service.dto.request.IntrospectRequest;
import com.towh.identity_service.dto.response.AuthenticationResponse;
import com.towh.identity_service.dto.response.IntrospectResponse;
import com.towh.identity_service.entity.User;
import com.towh.identity_service.exception.AppException;
import com.towh.identity_service.exception.ErrorCode;
import com.towh.identity_service.repository.UserRepository;
import lombok.*;
import lombok.experimental.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.*;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor // Lombok's annotation to generate a constructor with all final fields
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    // Secret key for signing the JWT token
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(User user) {
        // For JWT token header
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        // For JWT token payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("identity-service") // Domain of the service
                .issueTime(new Date())
                .audience("identity-service") // Domain of the service
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour
                .claim("scope", buildScope(user))
                .build();

        // Create payload
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (Exception e) {
            throw new AppException(ErrorCode.SIGNER_KEY_UNAUTHENTICATED);
        }
    }

    public IntrospectResponse introspectToken(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes()); // Create a verifier with the secret key

        SignedJWT signedJWT = SignedJWT.parse(token); // Parse the token

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime(); // Get the expiration time of the token

        var verified = signedJWT.verify(verifier); // Verify the token

        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }

    private String buildScope(User user) {
        StringJoiner joiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                joiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissionSet())) {
                    role.getPermissionSet().forEach(permission -> joiner.add(permission.getName()));
                }
            });
        }

        return joiner.toString();
    }

}
