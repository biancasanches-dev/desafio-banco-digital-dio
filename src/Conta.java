import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public abstract class Conta implements IConta {
	
	private static final int AGENCIA_PADRAO = 1;
	private static int SEQUENCIAL = 1;

	protected int agencia;
	protected int numero;
	protected double saldo;
	protected Cliente cliente;
	protected CartaoDeCredito cartao;
	protected Map<String, Double> transacoes = new HashMap();

	public Conta(Cliente cliente) {
		this.agencia = Conta.AGENCIA_PADRAO;
		this.numero = SEQUENCIAL++;
		this.cliente = cliente;
	}

	@Override
	public void sacar(double valor) {
		if(saldo >= valor) {
			saldo -= valor;
			transacoes.put("saque", valor);
		} else {
			System.out.println("\nNão há saldo suficiente para essa transação. Saldo: " + saldo);
		}
	}

	@Override
	public void depositar(double valor) {
		saldo += valor;
		transacoes.put("deposito", valor);
	}

	@Override
	public void transferir(double valor, IConta contaDestino) {
		if(saldo >= valor) {
			this.sacar(valor);
			contaDestino.depositar(valor);
			transacoes.put("transferencia", valor);
		} else {
			System.out.println("Não há saldo suficiente para essa transação. Saldo: " + saldo);
		}
	}

	public void imprimirExtrato() {
		System.out.println("=== Extrato ===");
		imprimirInfosComuns();
	}

	@Override
	public void imprimirExtratoDetalhado() {
		System.out.println("\n==================Extrato Detalhado=================");
		if(!transacoes.isEmpty()){
			for (Map.Entry<String, Double> entry : transacoes.entrySet()) {
				System.out.println("\n" + entry.getKey() + " Valor: R$ " + entry.getValue());
			}
			System.out.println("\nSaldo atual: R$ " + saldo);
		} else {
			System.out.println("\nSem movimentações na conta.");
		}
	}

	public void solicitarCartaoDeCredito(double limiteSolicitado) {
		if(this.saldo / 2 > limiteSolicitado) {
			cartao = new CartaoDeCredito(cliente);
			cartao.gerarCartao(limiteSolicitado);
		} else {
			System.out.println("Solicitação recusada. Seu limite mínimo para um cartão é de: " + getSaldo() * 2);
		}
	}

	public void solicitarNovoLimite(double novoLimite) {
		if(cartao != null) {
			if(novoLimite >= saldo /2) {
				cartao.alterarLimite(novoLimite);
				System.out.println("Limite alterado com sucesso.");
			} else {
				System.out.println("O novo limite deve ser nom máximo: " + saldo / 2);
			}
		} else {
			System.out.println("Você não possui um cartão de crédito.");
		}
	}

	public void pagarFatura(double valor) {
		if(this.saldo >= valor ){
			if(valor == cartao.getValorFatura()) {
				this.cartao.pagarFatura(valor);
				this.saldo -= valor;
				this.transacoes.put("pagamento de fatura", valor);
				System.out.println("\nPagamento efetuado no valor de R$ " + valor);
			}
		} else {
			System.out.println("\nSaldo insuficiente para pagamento da fatura.");
		}
	}

	protected void imprimirInfosComuns() {
		System.out.println(String.format("Titular: %s", this.cliente.getNome()));
		System.out.println(String.format("Agencia: %d", this.agencia));
		System.out.println(String.format("Numero: %d", this.numero));
		System.out.println(String.format("Saldo: %.2f", this.saldo));
	}
}
