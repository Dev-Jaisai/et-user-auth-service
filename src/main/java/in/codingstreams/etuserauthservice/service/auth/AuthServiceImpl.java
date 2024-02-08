package in.codingstreams.etuserauthservice.service.auth;

import in.codingstreams.etuserauthservice.data.model.AppUser;
import in.codingstreams.etuserauthservice.data.repo.AppUserRepo;
import in.codingstreams.etuserauthservice.exception.UserAlreadyExistException;
import in.codingstreams.etuserauthservice.service.model.AuthRequest;
import in.codingstreams.etuserauthservice.service.model.AuthResponse;
import in.codingstreams.etuserauthservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static in.codingstreams.etuserauthservice.constant.LoggingConstant.METHOD_LOG_END;
import static in.codingstreams.etuserauthservice.constant.LoggingConstant.METHOD_LOG_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(AuthRequest authRequest) {
        if (appUserRepo.existsByEmail(authRequest.getEmail())) {
            log.error(METHOD_LOG_ERROR,"signUo","UserAlreadyExistException");
            throw new UserAlreadyExistException("User Already Exist");

        }
        var password = passwordEncoder.encode(authRequest.getPassword());

        //create user
        var appUser = AppUser.builder().
                name(authRequest.getName())
                .email(authRequest.getEmail())
                .password(password).build();

        appUserRepo.save(appUser);

        var accessToken = JwtUtils.generateAccessToken(authRequest.getEmail());
        log.info(METHOD_LOG_END,"signUo");

        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();

    }
}
