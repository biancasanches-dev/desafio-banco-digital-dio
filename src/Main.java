
public class Main {

	public static void main(String[] args) {
		Cliente venilton = new Cliente();
		venilton.setNome("Venilton");
		
		Conta cc = new ContaCorrente(venilton);
		Conta poupanca = new ContaPoupanca(venilton);

		cc.depositar(2000);
		cc.transferir(100, poupanca);

		cc.sacar(200);
		poupanca.transferir(50, cc);

		cc.imprimirExtrato();

		cc.solicitarCartaoDeCredito(500);

		cc.cartao.desbloquearCartao();

		cc.cartao.solicitarNovoLimite(300);

		cc.cartao.comprarNoCartao(200.50);
		cc.cartao.imprimirFatura();
		cc.pagarFatura(200.50);

		cc.imprimirExtratoDetalhado();
	}

}
