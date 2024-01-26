package andreamarchica.U5W3L5.services;

import andreamarchica.U5W3L5.entities.Event;
import andreamarchica.U5W3L5.exceptions.NotFoundException;
import andreamarchica.U5W3L5.payloads.events.NewEventDTO;
import andreamarchica.U5W3L5.repositories.EventsRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class EventsService {
    @Autowired
    EventsRepository eventsRepository;
    @Autowired
    Cloudinary cloudinary;
    public Page<Event> getEvents(int page, int size, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return eventsRepository.findAll(pageable);
    }

    public Event save(NewEventDTO body) throws IOException {
        Event newEvent = new Event();
        newEvent.setTitle(body.title());
        newEvent.setDescription(body.description());
        newEvent.setEventDate(body.eventDate());
        newEvent.setLocation(body.location());
        newEvent.setEventImage(body.eventImage());
        newEvent.setAvailablePlaces(body.availablePlaces());
        return eventsRepository.save(newEvent);
    }

    public Event findById(UUID id) {
        return eventsRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Event found = this.findById(id);
        eventsRepository.delete(found);
    }

    public Event findByIdAndUpdate(UUID id, Event body) {

        Event found = this.findById(id);
        found.setTitle(body.getTitle());
        found.setEventDate(body.getEventDate());
        found.setEventImage(body.getEventImage());
        found.setDescription(body.getDescription());
        found.setLocation(body.getLocation());
        found.setAvailablePlaces(body.getAvailablePlaces());
        return eventsRepository.save(found);
    }
    public Event uploadEventImage(UUID id, MultipartFile file) throws IOException {
        Event found = this.findById(id);
        String eventImage = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setEventImage(eventImage);
        return eventsRepository.save(found);
    }
    public Event findByTitle(String title) throws NotFoundException {
        return eventsRepository.findByTitle(title).orElseThrow(() -> new NotFoundException("L'evento chiamato " + title + " non trovato!"));
    }
}
