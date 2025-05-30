package br.com.fiap.rm_550275.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.fiap.rm_550275.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
