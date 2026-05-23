package br.com.fiapbank.infrastructure;

import br.com.fiapbank.model.Cliente;
import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.ContaAcesso;
import br.com.fiapbank.model.ContaCorrente;
import br.com.fiapbank.model.ContaPoupanca;

import java.util.HashMap;
import java.util.Map;

public class BancoDados {

    // simula um banco de dados em memoria
    private Map<String, Conta> contas;
    private Map<String, ContaAcesso> acessos;

    private static BancoDados instancia;

    private BancoDados() {
        this.contas = new HashMap<>();
        this.acessos = new HashMap<>();
        carregarDadosFicticios();
    }

    public static BancoDados getInstancia() {
        if (instancia == null) {
            instancia = new BancoDados();
        }
        return instancia;
    }

    private void carregarDadosFicticios() {
        Cliente cliente1 = new Cliente("Joao Silva", "123.456.789-00");
        Cliente cliente2 = new Cliente("Maria Souza", "987.654.321-00");

        ContaCorrente cc1 = new ContaCorrente("0001-1", 1500.00, cliente1, 500.00);
        ContaPoupanca cp1 = new ContaPoupanca("0002-2", 3000.00, cliente2, 0.005);

        contas.put("0001-1", cc1);
        contas.put("0002-2", cp1);

        acessos.put("0001-1", new ContaAcesso("1234"));
        acessos.put("0002-2", new ContaAcesso("4321"));
    }

    public Conta buscarConta(String numeroConta) {
        return contas.get(numeroConta);
    }

    public ContaAcesso buscarAcesso(String numeroConta) {
        return acessos.get(numeroConta);
    }
}
