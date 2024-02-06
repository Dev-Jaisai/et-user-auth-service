package in.codingstreams.etuserauthservice.controller;

import in.codingstreams.etuserauthservice.controller.mapper.AuthRequestMapper;
import in.codingstreams.etuserauthservice.controller.mapper.AuthResponseMapper;
import in.codingstreams.etuserauthservice.controller.model.AuthRequestDto;
import in.codingstreams.etuserauthservice.controller.model.AuthResponseDto;
import in.codingstreams.etuserauthservice.service.auth.AuthService;
import in.codingstreams.etuserauthservice.service.model.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
        @PostMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody AuthRequestDto authRequestDto) {

        AuthResponse authResponse = authService.signUp(AuthRequestMapper.INSTANCE.toAuthRequest(authRequestDto));
        AuthResponseDto authResponseDto = AuthResponseMapper.INSTANCE.toAuthResponseDto(authResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponseDto);
    }
}
