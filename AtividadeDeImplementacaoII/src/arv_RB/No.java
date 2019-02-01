package arv_RB;

/*Implementacao do No da Rubro Negra*/

public class No implements ArvRB{
	
	private No esq; //Filho esquerdo
	private No dir; //Filho direito
	private No pai; //Pai do nó

	private int valor; //Dado guardado no nó da árvore
	private int cor; //guarda a cor atual do nó
	
	public No(){
		//O nó é uma folha, então é a folha é um nó nulo (NULL) e preto
		this.cor = ArvRB.BLACK;
		setEsq(null);
		setDir(null);
		setPai(null);		
	};
	
	public No(int valor){
		this.valor = valor; 
		//A cor do nó criado e inserido na RB é vermelha
		this.cor = ArvRB.RED;	
	}
	
	//Metodos de acesso e modificadores
	public No getEsq() {
		return esq;
	}

	public void setEsq(No esq) {
		this.esq = esq;
	}

	public No getDir() {
		return dir;
	}

	public void setDir(No dir) {
		this.dir = dir;
	}
	
	public No getPai() {
		return pai;
	}

	public void setPai(No pai) {
		this.pai = pai;
	}


	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public int getCor() {
		return cor;
	}

	public void setCor(int cor) {
		this.cor = cor;
	}

}

