package br.com.fiapbank.model;

import br.com.fiapbank.model.exceptions.SaldoInsuficienteException;
import br.com.fiapbank.model.exceptions.ValorInvalidoException;

public abstract class Conta {

    private String numeroConta;
    private Double saldo;
    private Cliente titular;

    public Conta(String numeroConta, Double saldoInicial, Cliente titular) {
        if (saldoInicial < 0) {
            throw new ValorInvalidoException("Saldo inicial nao pode ser negativo.");
        }
        this.numeroConta = numeroConta;
        this.saldo = saldoInicial;
        this.titular = titular;
    }

    public void depositar(Double valor) {
        if (valor == null || valor <= 0) {
            throw new ValorInvalidoException("Valor de deposito invalido. Informe um valor positivo.");
        }
        this.saldo += valor;
    }

    public void sacar(Double valor) {
        if (valor == null || valor <= 0) {
            throw new ValorInvalidoException("Valor de saque invalido. Informe um valor positivo.");
        }
        if (valor > this.saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente. Saldo atual: R$ " + String.format("%.2f", this.saldo));
        }
        this.saldo -= valor;
    }

    public Double getSaldo() {
        return saldo;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public Cliente getTitular() {
        return titular;
    }

    public abstract String getTipoConta();

    @Override
    public String toString() {
        return "Conta [" + getTipoConta() + "] | Numero: " + numeroConta
                + " | Titular: " + titular.getNome()
                + " | Saldo: R$ " + String.format("%.2f", saldo);
    }
}
