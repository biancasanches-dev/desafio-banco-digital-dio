public interface ICartaoDeCredito {

    void gerarCartao(double limite);

    void imprimirFatura();

    void comprarNoCartao(double valor);

    void pagarFatura(double valor);

    void bloquearCartao();

    void desbloquearCartao();

    void alterarLimite(double novoLimite);
}
