package planIT.Entity.Events;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import planIT.Entity.Users.User;
import planIT.Entity.Users.UserService;

/**
 * Controller class for event entity
 * @author Melani Hodge
 *
 */
@RestController
@Tag(name = "Event Management System", description = "Operations pertaining to event management")
public class EventController {

    // @Autowired - Injects implementation of the repository interface without the need for explicit bean configuration.
    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    // GET method - retreives all events from the database.
    /**
     * Gets all of an event's users and returns them as a List
     * @return events list
     */
    @GetMapping(path = "/events")
    @Operation(summary = "Get all Events", description = "Get all of an event's users and return them as a List")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // GET method - retreives a event from the database.
    /**
     * Gets an event from repository by its id number
     * @param id id number of target event
     * @return event
     */
    @GetMapping(path = "/events/{id}")
    @Operation(summary = "Get an Event by Id", description = "Get an event from the repository by its id number")
    public Event getEventById(@PathVariable int id) {
        return eventService.getEventById(id);
    }

    // POST method - adds an event to the database.
    /**
     * Creates a new event and attaches it to a user
     * @param username username of target user
     * @param event event to be created
     * @return success
     */
    @PostMapping(path = "users/{username}/events")
    @Operation(summary = "Create a new Event for a user", description = "Create a new event and attach it to a user")
    public Event createEvent(@PathVariable String username, @RequestBody Event event) {
        return eventService.createEvent(username, event);
    }

    /**
     * Adds a preexisting user to a preexisting event
     * @param username username of target user
     * @param eventId id number of target event
     * @return success
     */
    @PutMapping(path = "/users/{username}/events/{eventId}")
    @Operation(summary = "Add a user to an Event", description = "Add a preexisting user to a preexisting event")
    public String addUserToEvent(@PathVariable String username, @PathVariable int eventId) {
        return eventService.addUserToEvent(username, eventId);
    }

    // GET method - adds an event to the database.
    /**
     * Returns all events a particular user has as a set.
     * @param username username of target user
     * @return Events
     */
    @GetMapping(path = "/users/{username}/events")
    @Operation(summary = "Get Events for a user", description = "Returns all events a particular user has as a set")
    public Set<Event> getUserEvents(@PathVariable String username) {
        return eventService.getUserEvents(username);
    }

    // PUT method - updates an event in the database.
    /**
     * Updates an event in the repository
     * @param id id number of event to be updated
     * @param event event object with updated details
     * @return event
     */
    @PutMapping(path = "/events/{id}")
    @Operation(summary = "Update an existing Event", description = "Updates an event in the repository")
    public Event updateEvent(@PathVariable int id, @RequestBody Event event) {
        return eventService.updateEvent(id, event);
    }

    // DELETE method - deletes an event from the database.
    /**
     * Deletes am event from the repository
     * @param username ...
     * @param id id number of target event
     * @return success
     */
    @DeleteMapping(path = "users/{username}/events/{id}")
    @Operation(summary = "Delete an Event by Id", description = "Deletes an event from the repository")
    public String deleteEvent(@PathVariable String username, @PathVariable int id) {
        eventService.getEventById(id).getUsers().clear();
        User user = userService.findUserByUsername(username);
        user.getEvents().remove(eventService.getEventById(id));
        user.getManaged().remove(eventService.getEventById(id));
        return eventService.deleteEvent(username, id);
    }
}

