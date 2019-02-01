package arv_B;

public interface BT_Interface {
	//Inserir
	public void btInsert(int k);
	//Busca
	public No btQuery(No X, int k);
	//Remoção
    public void btRemove(int k);
	//Limpar dados
	 public void btDestroy(No N, int ordem);
	 //Imprimir na Arvore
	 public void btEm_ordem(No node);
}
