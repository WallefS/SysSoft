package br.ueg.posse.soft.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "empresa")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "A Razao social é obrigatória")
	@Size(max = 100, message = "A Razao social não pode conter mais de 100 caracteres")
	@Column(name = "nome", nullable = false)
	private String nome;

	@CNPJ
	@Column(name = "cnpj", nullable = false)
	private String cnpj;
	@NotEmpty(message = "O endereço é obrigatório")
	@Size(max = 60, message = "O endereço não pode conter mais de 60 caracteres")
	@Column(name = "endereco", nullable = false)
	private String endereco;

	@Column(name = "numero", nullable = false)
	private String numero;

	@Column(name = "bairro", nullable = false)
	private String bairro;
	@NotEmpty(message = "O Telefone é obrigatório")
	@Size(max = 60, message = "O Telefone não pode conter mais de 60 caracteres")
	@Column(name = "telefone1", nullable = false)
	private String telefone1;

	@Column(name = "telefone", nullable = false)
	private String telefone;

	@Email
	@Column(name = "email", nullable = false)
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
