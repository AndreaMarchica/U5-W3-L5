package andreamarchica.U5W3L5.services;

import andreamarchica.U5W3L5.entities.User;
import andreamarchica.U5W3L5.exceptions.NotFoundException;
import andreamarchica.U5W3L5.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;


    public Page<User> getUsers(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return usersRepository.findAll(pageable);
    }


    public User findById(UUID id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        User found = this.findById(id);
        usersRepository.delete(found);
    }

    public User findByIdAndUpdate(UUID id, User body) {

        User found = this.findById(id);
        found.setUsername(body.getUsername());
        found.setEmail(body.getEmail());
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setPassword(body.getPassword());
        return usersRepository.save(found);
    }

    public User findByEmail(String email) throws NotFoundException {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovata!"));
    }
}
