package andreamarchica.U5W3L5.services;

import andreamarchica.U5W3L5.entities.Role;
import andreamarchica.U5W3L5.entities.User;
import andreamarchica.U5W3L5.exceptions.BadRequestException;
import andreamarchica.U5W3L5.exceptions.UnauthorizedException;
import andreamarchica.U5W3L5.payloads.login.UserLoginDTO;
import andreamarchica.U5W3L5.payloads.users.NewUserDTO;
import andreamarchica.U5W3L5.repositories.UsersRepository;
import andreamarchica.U5W3L5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public String authenticateUser(UserLoginDTO body) {
        User user = usersService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
    public User save(NewUserDTO body) throws IOException {
        usersRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + body.email() + " è già stata utilizzata");
        });
        User newUser = new User();
        newUser.setUsername(body.userName());
        newUser.setName(body.name());
        newUser.setEmail(body.email());
        newUser.setSurname(body.surname());
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setRole(Role.USER);
        return usersRepository.save(newUser);
    }
}
