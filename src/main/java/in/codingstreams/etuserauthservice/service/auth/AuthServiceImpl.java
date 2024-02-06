package in.codingstreams.etuserauthservice.service.auth;

import in.codingstreams.etuserauthservice.data.model.AppUser;
import in.codingstreams.etuserauthservice.data.repo.AppUserRepo;
import in.codingstreams.etuserauthservice.exception.UserAlreadyExistException;
import in.codingstreams.etuserauthservice.service.model.AuthRequest;
import in.codingstreams.etuserauthservice.service.model.AuthResponse;
import in.codingstreams.etuserauthservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse signUp(AuthRequest authRequest) {
        if (appUserRepo.existsByEmail(authRequest.getEmail())) {
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

        return AuthResponse.builder()
                .accessToken(accessToken)
                .build();

    }
}
