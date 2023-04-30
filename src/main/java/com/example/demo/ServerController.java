package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/servers")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @GetMapping("")
    public List<Server> getAllServers() {
        return serverService.getAllServers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Server> getServerById(@PathVariable String id) throws ChangeSetPersister.NotFoundException {
        Optional<Server> server = Optional.ofNullable(serverService.getServerById(id));
        if (server.isPresent()) {
            return ResponseEntity.ok(server.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("")
    public ResponseEntity<Server> createServer(@RequestBody Server server) {
        Server createdServer = serverService.createServer(server);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdServer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServer(@PathVariable String id) throws ChangeSetPersister.NotFoundException {
        serverService.deleteServer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Server>> searchServers(@RequestParam String name) {
        List<Server> servers = serverService.searchServers(name);
        if (servers.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(servers);
        }
    }
}
