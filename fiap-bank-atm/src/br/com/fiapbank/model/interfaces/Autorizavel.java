package br.com.fiapbank.model.interfaces;

public interface Autorizavel {
    Boolean autorizar(String senha);
    Boolean isBloqueado();
}
