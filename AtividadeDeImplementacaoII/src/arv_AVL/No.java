package arv_AVL;

/*Implementacao do No da AVL*/
public class No {
	
	private No esq; //Filho esquerdo
	private No dir; //Filho direito
	private int valor; //Dado guardado no Nó
	private int altura; //guarda a altura em que o nó se encontra na árvore
	
	public No(){};
	
	//Construtor
	public No(int valor){
		this.valor = valor; 
		this.altura = 0;
		setEsq(null);
		setDir(null);
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

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	
	
	
}

