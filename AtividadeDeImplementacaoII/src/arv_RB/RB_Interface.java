package arv_RB;

public interface RB_Interface {

	public void rbInsert(No node, int valor);
	public No rbQuery(No node, int valor, int estado);
	public void rbRemove(No node, int valor);
	public void rbDestroy(No node);

}
