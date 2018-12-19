package br.ueg.posse.soft.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ueg.posse.soft.model.Cliente;
import br.ueg.posse.soft.repository.helper.cliente.ClientesQueries;

public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {

	public Optional<Cliente> findByCpfOuCnpj(String cpfOuCnpj);

}
