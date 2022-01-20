package br.com.dio.service;

import br.com.dio.model.Client;

/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 *
 * @author falvojr
 */

public interface ClientService {

    Iterable<Client> findAll();

    Client findById(Long id);

    void createClient(Client client);

    void updateClient(Long id, Client client);

    void deleteClient(Long id);
}
