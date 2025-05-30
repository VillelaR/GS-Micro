package br.com.fiap.rm_550275.controller;

import br.com.fiap.rm_550275.dto.ProdutoDTO;
import br.com.fiap.rm_550275.model.Produto;
import br.com.fiap.rm_550275.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody ProdutoDTO dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        return ResponseEntity.ok(produtoService.save(produto));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getAll() {
        List<ProdutoDTO> produtos = produtoService.findAll().stream().map(produto -> {
            ProdutoDTO dto = new ProdutoDTO();
            dto.setId(produto.getId());
            dto.setNome(produto.getNome());
            dto.setDescricao(produto.getDescricao());
            dto.setPreco(produto.getPreco());
            dto.setQuantidade(produto.getQuantidade());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> getById(@PathVariable Long id) {
        return produtoService.findById(id).map(produto -> {
            ProdutoDTO dto = new ProdutoDTO();
            dto.setId(produto.getId());
            dto.setNome(produto.getNome());
            dto.setDescricao(produto.getDescricao());
            dto.setPreco(produto.getPreco());
            dto.setQuantidade(produto.getQuantidade());
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody ProdutoDTO dto) {
        if (!produtoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setQuantidade(dto.getQuantidade());
        return ResponseEntity.ok(produtoService.save(produto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
