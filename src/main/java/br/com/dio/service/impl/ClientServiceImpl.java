package br.com.dio.service.impl;

import br.com.dio.model.Address;
import br.com.dio.model.Client;
import br.com.dio.repository.AddressRepository;
import br.com.dio.repository.ClientRepository;
import br.com.dio.service.ClientService;
import br.com.dio.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementação da <b>Strategy</b> {@link ClientService}, a qual pode ser
 * injetada pelo Spring (via {@link Autowired}). Com isso, como essa classe é um
 * {@link Service}, ela será tratada como um <b>Singleton</b>.
 *
 * @author falvojr
 */

@Service
public class ClientServiceImpl implements ClientService {

    // Singleton: Injetar os componentes do Spring com @Autowired.
    @Autowired private ClientRepository clientRepository;
    @Autowired private AddressRepository addressRepository;
    @Autowired private ViaCepService viaCepService;

    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair integrações com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> client = clientRepository.findById(id);
        return client.get();
    }

    @Override
    public void createClient(Client client) {
        saveClientWithCep(client);
    }

    @Override
    public void updateClient(Long id, Client client) {
        Optional<Client> clientDb = clientRepository.findById(id);
            if(clientDb.isPresent()){
                saveClientWithCep(client);
            }
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    private void saveClientWithCep(Client client) {
        // Verificar se o Endereco do Cliente já existe (pelo CEP).
        String cep = client.getAddress().getCep();
        Address address = addressRepository.findById(cep).orElseGet(() -> {
            // Caso não exista, integrar com o ViaCEP e persistir o retorno.
            Address newAddress = viaCepService.consultCep(cep);
            addressRepository.save(newAddress);
            return newAddress;
        });
        client.setAddress(address);
        // Inserir Cliente, vinculando o Endereco (novo ou existente).
        clientRepository.save(client);
    }
}
