package br.fabio.professor.modelo;

public class Conta {
    private long id;
    private String titulo;
    private double valor;
    private Usuario usuario;

    public Conta(){}
    public Conta(long id, String titulo, double valor, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.valor = valor;
        this.usuario = usuario;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}