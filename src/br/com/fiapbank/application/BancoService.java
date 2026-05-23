package br.com.fiapbank.application;

import br.com.fiapbank.infrastructure.BancoDados;
import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.ContaAcesso;
import br.com.fiapbank.model.exceptions.SaldoInsuficienteException;
import br.com.fiapbank.model.exceptions.ValorInvalidoException;

public class BancoService {

    private BancoDados bancoDados;

    public BancoService() {
        this.bancoDados = BancoDados.getInstancia();
    }

    public Conta autenticar(String numeroConta, String senha) {
        Conta conta = bancoDados.buscarConta(numeroConta);
        if (conta == null) {
            throw new ValorInvalidoException("Conta nao encontrada: " + numeroConta);
        }
        ContaAcesso acesso = bancoDados.buscarAcesso(numeroConta);
        acesso.autorizar(senha); // lanca excecao se errar
        return conta;
    }

    public void realizarSaque(Conta conta, Double valor) {
        conta.sacar(valor);
    }

    public void realizarDeposito(Conta conta, Double valor) {
        conta.depositar(valor);
    }

    public Double consultarSaldo(Conta conta) {
        return conta.getSaldo();
    }
}
