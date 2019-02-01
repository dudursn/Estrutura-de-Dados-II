package arv_AVL;

public interface AVL_Interface {
	//Insere
	public No avlInsert(No node, int valor);
	//Busca
	public void avlQuery(No node, int valor);
	//Remove
	public No avlRemove(No node, int valor);
	//Destrói
	public void avlDestroy(No node);
}
