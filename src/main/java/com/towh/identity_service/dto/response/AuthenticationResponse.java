package com.towh.identity_service.dto.response;

import lombok.*;
import lombok.experimental.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse {
    boolean authenticated;
    String token;
}
