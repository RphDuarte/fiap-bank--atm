package br.com.fiapbank.model;

public class ContaPoupanca extends Conta {

    private Double taxaRendimento;

    public ContaPoupanca(String numeroConta, Double saldoInicial, Cliente titular, Double taxaRendimento) {
        super(numeroConta, saldoInicial, titular);
        this.taxaRendimento = taxaRendimento;
    }

    public void aplicarRendimento() {
        Double rendimento = getSaldo() * taxaRendimento;
        depositar(rendimento);
        System.out.println("Rendimento aplicado: R$ " + String.format("%.2f", rendimento));
    }

    public Double getTaxaRendimento() {
        return taxaRendimento;
    }

    @Override
    public String getTipoConta() {
        return "Conta Poupanca";
    }

    @Override
    public String toString() {
        return super.toString() + " | Taxa de Rendimento: " + (taxaRendimento * 100) + "%";
    }
}
