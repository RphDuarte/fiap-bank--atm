package br.com.fiapbank.model;

import br.com.fiapbank.model.exceptions.AcessoBloqueadoException;
import br.com.fiapbank.model.exceptions.SenhaInvalidaException;
import br.com.fiapbank.model.interfaces.Autorizavel;

public class ContaAcesso implements Autorizavel {

    private static final Integer MAX_TENTATIVAS = 3;

    private String senhaCadastrada;
    private Integer tentativasErradas;
    private Boolean bloqueado;

    public ContaAcesso(String senhaCadastrada) {
        this.senhaCadastrada = senhaCadastrada;
        this.tentativasErradas = 0;
        this.bloqueado = false;
    }

    @Override
    public Boolean autorizar(String senha) {
        if (isBloqueado()) {
            throw new AcessoBloqueadoException("Acesso bloqueado! Voce atingiu o limite de " + MAX_TENTATIVAS + " tentativas erradas. Procure uma agencia FIAP Bank.");
        }

        if (senha == null || senha.isEmpty()) {
            throw new SenhaInvalidaException("Senha nao pode ser vazia.");
        }

        if (senha.equals(this.senhaCadastrada)) {
            this.tentativasErradas = 0;
            return true;
        } else {
            this.tentativasErradas++;
            Integer tentativasRestantes = MAX_TENTATIVAS - this.tentativasErradas;

            if (this.tentativasErradas >= MAX_TENTATIVAS) {
                this.bloqueado = true;
                throw new AcessoBloqueadoException("Senha incorreta. Conta bloqueada por excesso de tentativas! Procure uma agencia FIAP Bank.");
            }

            throw new SenhaInvalidaException("Senha incorreta. Tentativas restantes: " + tentativasRestantes);
        }
    }

    @Override
    public Boolean isBloqueado() {
        return bloqueado;
    }

    public Integer getTentativasErradas() {
        return tentativasErradas;
    }
}
