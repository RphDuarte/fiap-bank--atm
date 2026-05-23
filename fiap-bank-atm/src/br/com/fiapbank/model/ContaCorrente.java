package br.com.fiapbank.model;

import br.com.fiapbank.model.exceptions.SaldoInsuficienteException;
import br.com.fiapbank.model.exceptions.ValorInvalidoException;

public class ContaCorrente extends Conta {

    private Double limite;

    public ContaCorrente(String numeroConta, Double saldoInicial, Cliente titular, Double limite) {
        super(numeroConta, saldoInicial, titular);
        if (limite == null || limite < 0) {
            throw new ValorInvalidoException("Limite da conta corrente nao pode ser negativo.");
        }
        this.limite = limite;
    }

    @Override
    public void sacar(Double valor) {
        if (valor == null || valor <= 0) {
            throw new ValorInvalidoException("Valor de saque invalido. Informe um valor positivo.");
        }

        Double saldoDisponivel = getSaldo() + limite;

        if (valor > saldoDisponivel) {
            throw new SaldoInsuficienteException(
                "Saldo insuficiente. Saldo disponivel (saldo + limite): R$ " + String.format("%.2f", saldoDisponivel)
            );
        }

        // Se tem saldo suficiente, usa so o saldo
        if (valor <= getSaldo()) {
            super.sacar(valor);
        } else {
            // Usa o saldo todo e o restante vai do limite
            Double valorDoLimite = valor - getSaldo();
            super.sacar(getSaldo()); // zera o saldo
            this.limite -= valorDoLimite;
        }
    }

    public Double getLimite() {
        return limite;
    }

    @Override
    public String getTipoConta() {
        return "Conta Corrente";
    }

    @Override
    public String toString() {
        return super.toString() + " | Limite: R$ " + String.format("%.2f", limite);
    }
}
