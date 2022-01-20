package br.com.dio.controller;

import br.com.dio.model.Client;
import br.com.dio.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 *
 * @author falvojr
 */

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired private ClientService clientService;

    @GetMapping
    public ResponseEntity<Iterable<Client>> findAll(){
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById(@PathVariable Long id){
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        clientService.createClient(client);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client){
        clientService.updateClient(id, client);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}
