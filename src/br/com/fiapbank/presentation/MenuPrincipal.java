package br.com.fiapbank.presentation;

import br.com.fiapbank.application.BancoService;
import br.com.fiapbank.model.Conta;
import br.com.fiapbank.model.exceptions.AcessoBloqueadoException;
import br.com.fiapbank.model.exceptions.SaldoInsuficienteException;
import br.com.fiapbank.model.exceptions.SenhaInvalidaException;
import br.com.fiapbank.model.exceptions.ValorInvalidoException;

import java.util.Scanner;

public class MenuPrincipal {

    private BancoService bancoService;
    private Scanner scanner;

    public MenuPrincipal() {
        this.bancoService = new BancoService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("=========================================");
        System.out.println("    BEM-VINDO AO FIAP BANK ATM          ");
        System.out.println("=========================================");

        Conta contaLogada = realizarLogin();

        if (contaLogada == null) {
            System.out.println("Encerrando sessao...");
            return;
        }

        exibirMenuOperacoes(contaLogada);
    }

    private Conta realizarLogin() {
        System.out.print("Digite o numero da sua conta: ");
        String numeroConta = scanner.nextLine().trim();

        Conta conta = null;

        while (conta == null) {
            try {
                System.out.print("Digite sua senha: ");
                String senha = scanner.nextLine().trim();
                conta = bancoService.autenticar(numeroConta, senha);
                System.out.println("\nAutenticacao realizada com sucesso!");
                System.out.println("Seja bem-vindo(a), " + conta.getTitular().getNome() + "!");
            } catch (AcessoBloqueadoException e) {
                System.out.println("\n[ERRO DE ACESSO] " + e.getMessage());
                return null;
            } catch (SenhaInvalidaException e) {
                System.out.println("\n[AVISO] " + e.getMessage());
                System.out.print("Deseja tentar novamente? (s/n): ");
                String resposta = scanner.nextLine().trim();
                if (!resposta.equalsIgnoreCase("s")) {
                    return null;
                }
            } catch (ValorInvalidoException e) {
                System.out.println("\n[ERRO] " + e.getMessage());
                return null;
            }
        }

        return conta;
    }

    private void exibirMenuOperacoes(Conta conta) {
        Integer opcao = 0;

        while (!opcao.equals(4)) {
            System.out.println("\n-----------------------------------------");
            System.out.println("  MENU PRINCIPAL - " + conta.getTitular().getNome());
            System.out.println("-----------------------------------------");
            System.out.println("  [1] Consultar Saldo");
            System.out.println("  [2] Realizar Saque");
            System.out.println("  [3] Realizar Deposito");
            System.out.println("  [4] Sair");
            System.out.println("-----------------------------------------");
            System.out.print("Escolha uma opcao: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());

                switch (opcao) {
                    case 1:
                        consultarSaldo(conta);
                        break;
                    case 2:
                        realizarSaque(conta);
                        break;
                    case 3:
                        realizarDeposito(conta);
                        break;
                    case 4:
                        System.out.println("\nObrigado por usar o FIAP Bank ATM. Ate logo!");
                        break;
                    default:
                        System.out.println("[AVISO] Opcao invalida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[AVISO] Por favor, digite apenas numeros.");
            }
        }
    }

    private void consultarSaldo(Conta conta) {
        System.out.println("\n--- CONSULTA DE SALDO ---");
        System.out.println("Conta: " + conta.getNumeroConta());
        System.out.println("Titular: " + conta.getTitular().getNome());
        System.out.printf("Saldo disponivel: R$ %.2f%n", bancoService.consultarSaldo(conta));
    }

    private void realizarSaque(Conta conta) {
        System.out.println("\n--- SAQUE ---");
        System.out.printf("Saldo atual: R$ %.2f%n", conta.getSaldo());
        System.out.print("Digite o valor do saque: R$ ");

        try {
            Double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            bancoService.realizarSaque(conta, valor);
            System.out.println("\n[SUCESSO] Saque realizado com sucesso!");
            System.out.printf("Valor sacado: R$ %.2f%n", valor);
            System.out.printf("Novo saldo: R$ %.2f%n", conta.getSaldo());
        } catch (SaldoInsuficienteException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
            System.out.println("Operacao cancelada. Voltando ao menu...");
        } catch (ValorInvalidoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] Valor invalido. Digite um numero valido.");
        }
    }

    private void realizarDeposito(Conta conta) {
        System.out.println("\n--- DEPOSITO ---");
        System.out.print("Digite o valor do deposito: R$ ");

        try {
            Double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            bancoService.realizarDeposito(conta, valor);
            System.out.println("\n[SUCESSO] Deposito realizado com sucesso!");
            System.out.printf("Valor depositado: R$ %.2f%n", valor);
            System.out.printf("Novo saldo: R$ %.2f%n", conta.getSaldo());
        } catch (ValorInvalidoException e) {
            System.out.println("\n[ERRO] " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\n[ERRO] Valor invalido. Digite um numero valido.");
        }
    }
}
