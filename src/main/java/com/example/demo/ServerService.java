package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;

    public List<Server> getAllServers() {
        return serverRepository.findAll();
    }

    public Server getServerById(String id) throws ChangeSetPersister.NotFoundException {
        return serverRepository.findById(id)
                .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
    }

    public Server createServer(Server server) {
        return serverRepository.save(server);
    }

    public void deleteServer(String id) throws ChangeSetPersister.NotFoundException {
        if (!serverRepository.existsById(id)) {
            throw new ChangeSetPersister.NotFoundException();
        }
        serverRepository.deleteById(id);
    }

    public List<Server> searchServers(String name) {
        return serverRepository.findByNameContaining(name);
    }
}

