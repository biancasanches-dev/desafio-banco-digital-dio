import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class CartaoDeCredito extends Conta implements ICartaoDeCredito {

    private Set<Long> numerosDeCartao = new HashSet<>();
    private Long numeroDoCartao;
    private String dataDeValidade;
    private String bandeira;
    private double limite;
    private List<Double> fatura = new ArrayList<>();
    private double valorFatura;
    private boolean isBloqueado = true;

    public CartaoDeCredito(Cliente cliente) {
        super(cliente);
    }

    public void imprimirInfosCartao() {
        System.out.println("\n=== Cartão de Crédito ===");
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Bandeira: " + bandeira);
        System.out.println("Número: " + numeroDoCartao);
        System.out.println("Data de validade: " + dataDeValidade);
        System.out.println("Limite: " + limite);
        System.out.println("Situação atual: " + (isBloqueado ? "bloqueado" : "desbloqueado"));
    }

    public void pagarFatura(double valor) {
        if(valorFatura == 0) {
            System.out.println("\nNão há fatura para ser paga.");
        }
        if(valor == valorFatura) {
            this.fatura.clear();
            this.limite += valorFatura;
            System.out.println("\nFatura paga com sucesso!");
        } else {
            System.out.println("\nValor incorreto/insuficiente para pagar a fatura.");
        }
    }

    public void comprarNoCartao(double valor) {
        if(this.limite >= valor) {
            if (this.isBloqueado) {
                System.out.println("\nCartão bloqueado. Não é possível realizar a compra.");
                return;
            }
            this.fatura.add(valor);
            this.limite -= valor;
            System.out.println("\nCompra efetuada no valor de R$ " + valor);
        } else {
            System.out.println("\nVocê não possui limite para essa transação.");
        }
    }

    private void criarNumero() {
        Random rand = new Random();
        Long numero;
        do {
            numero = (long) (rand.nextDouble() * 10000000000000000L);
        } while (this.numerosDeCartao.contains(numero));
        this.numeroDoCartao = numero;
        this.numerosDeCartao.add(numeroDoCartao);
    }

    public void gerarCartao(double limite) {
        criarNumero();
        this.bandeira = "VISA";
        this.dataDeValidade = "10/10/2026";
        this.limite = limite;
        imprimirInfosCartao();
    }

    public void desbloquearCartao() {
        this.isBloqueado = false;
        System.out.println("\nCartão desbloqueado com sucesso!");
    }

    public void bloquearCartao() {
        this.isBloqueado = true;
        System.out.println("Cartão bloqueado com sucesso!");
    }

    public void alterarLimite(double valor) {
        this.limite = valor;
        System.out.println("\nLimite alterado para R$ " + valor);
    }

    private void calcularFatura() {
        double total = 0;
        if(!this.fatura.isEmpty()) {
            for (double valor : this.fatura) {
                total += valor;
            }
        }
        this.valorFatura = total;
    }

    public void imprimirFatura() {
        System.out.println("=== Fatura Cartão de Crédito ===");
        calcularFatura();
        System.out.println("\nTotal da fatura: " + this.valorFatura);
    }
}
