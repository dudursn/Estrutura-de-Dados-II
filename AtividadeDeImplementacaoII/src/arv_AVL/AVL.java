package arv_AVL;

import java.util.ArrayList;

public class AVL implements AVL_Interface{
	
	public No root;
	
	/*Construtor da arvore AVL, iniciando o No raiz como NULL*/
	public AVL(){
		this.root = null;
	};
	
	
	/*Calcula o fator de balanceamento da AVL: 
	 * +2, +1 se estiver mais a direita ou 
	 * -2, -1 se estiver mais a esquerda 
	 * e 0, se estiver em equilíbrio  */
	private int FatorBalanceamento(No node){
		return (Altura_No(node.getDir()) - Altura_No(node.getEsq()));
	}
	
	/*Calcula a altura do nó*/
	//Parâmetros: h1 - atura da subárvore esquerda, h2- atura da subárvore direita, maior - guarda a maior altura entre h1 e h2
	private int Altura_No(No node){
		
		if(node!=null){
			int h1 = Altura_No(node.getEsq());			
			int h2 = Altura_No(node.getDir());
			int maior = Maior_Altura(h1, h2);
			return maior+1; 
		}
		return 0;
	}
	
	//Maior altura
	/*Parâmetros: x - guarda a altura de uma subarvore, y - guarda a altura de uma subarvore*/
	public int Maior_Altura(int x, int y){
		if(x > y){
			return x;
		}else{
			return y;
		}
	}
	
	//Rotação Simples a direita
	/*Parâmetros: node - nó crítico da arvore, aux - nó auxiliar que recebe o filho esquerdo do nó crítico*/
	private No rotacaoSimplesDireita(No node){
		
		No aux;
		aux = node.getEsq();
		node.setEsq(aux.getDir());
		aux.setDir(node);
		
		//Recalcula a altura dos nós após sofrerem a rotação		
		node.setAltura(Maior_Altura(Altura_No(node.getEsq()), Altura_No(node.getDir())) + 1);
		aux.setAltura(Maior_Altura(Altura_No(node.getEsq()), node.getAltura()) + 1);
		
		return aux;
	}
	
	//Rotação Simples a esquerda
	/*Parâmetros: node - nó crítico da arvore, aux - nó auxiliar que recebe o filho direito do nó crítico*/
	private No rotacaoSimplesEsquerda(No node){
			
		No aux;
		aux = node.getDir();
		node.setDir(aux.getEsq());
		aux.setEsq(node);
			
		//Recalcula a altura dos nós após sofrerem a rotação
		node.setAltura(Maior_Altura(Altura_No(node.getEsq()), Altura_No(node.getDir())) + 1);
		aux.setAltura(Maior_Altura(Altura_No(node.getEsq()), node.getAltura()) + 1);
	
		return aux;
	}
	
	//Rotação Dupla a direita
	//Parâmetros: node - nó crítico da arvore
	private No rotacaoDuplaDireita(No node){
		node.setEsq(rotacaoSimplesEsquerda(node.getEsq()));
		return rotacaoSimplesDireita(node);
	}
	
	//rotação Dupla a esquerda
	//Parâmetros: node - nó crítico da arvore
	private No rotacaoDuplaEsquerda(No node){
		node.setDir(rotacaoSimplesDireita(node.getDir()));
		return rotacaoSimplesEsquerda(node);
		
	}
	
	//Inserção na AVL
	/*newNode - nó criado e inserido na arvore*/
	public No avlInsert(No node, int valor){
		if(node == null){
			/*Cria o nó*/
			No newNode = new No(valor);
			
			if(root==null){
				/*O nó criado passa a ser a raiz*/
				root = newNode;
			}
			
			System.out.println("Elemento inserido!");
			return newNode;
			
		} else{

			if(valor < node.getValor()){
				
				node.setEsq(avlInsert(node.getEsq(), valor));
								
			} else if(valor > node.getValor()){
				
				node.setDir(avlInsert(node.getDir(), valor));

			}else{
				System.out.println("Elemento repetido - não inserido!");
				return node;
			}
			/*Calcula a altura de cada nó*/
			node.setAltura(Maior_Altura(Altura_No(node.getEsq()), Altura_No(node.getDir())) + 1);
			
			/*Verifica o fator de balanceamento Se +2, e  o valor for maior que o valor do nó crítico, a arvore sofre rotação simple a esquerda, 
			 * se não rotação dupla a esquerda*/
			if(FatorBalanceamento(node)>1){
				if(valor > node.getDir().getValor()){
					return rotacaoSimplesEsquerda(node);
				} else{
					return rotacaoDuplaEsquerda(node);	
				}
			}
			
			/*Verifica o fator de balanceamento Se -2, e  o valor for maior que o valor do nó crítico, a arvore sofre rotação simple a esquerda, 
			 * se não rotação dupla a esquerda*/	
			if(FatorBalanceamento(node)<-1){
				if(valor < node.getEsq().getValor()){
					return rotacaoSimplesDireita(node);
				} else{
				return rotacaoDuplaDireita(node);
				}
			}
			
			return node;
			
		}
		
	}
	
	/*Remoção*/
	public No avlRemove(No node, int valor){
		
		No aux;
		if(node !=null){
			if(node.getValor() < valor){
				node.setDir(avlRemove(node.getDir(), valor));
				node.setAltura(Maior_Altura(Altura_No(node.getEsq()), Altura_No(node.getDir())) + 1);
				if(FatorBalanceamento(node)<-1){
					//Verifica se o filho da direita do filho do nó pai, o qual é crítico, existe, se sim rotação simples a direita, se não, duplamente a direita
					if(node.getEsq().getEsq()!=null){
						return rotacaoSimplesDireita(node);
					} else{
						return rotacaoDuplaDireita(node);
					}
				}
				
			}else if (node.getValor() > valor){
				node.setEsq(avlRemove(node.getEsq(), valor));
				node.setAltura(Maior_Altura(Altura_No(node.getEsq()), Altura_No(node.getDir())) + 1);
				if(FatorBalanceamento(node)>1){
					//Verifica se o filho da esquerda do filho do nó pai, o qual é crítico, existe, se sim rotação simples a esquerda, se não, duplamente a esquerda
					if(node.getDir().getDir()!=null){
						return rotacaoSimplesEsquerda(node);
					} else{
						return rotacaoDuplaEsquerda(node);	
					}
				}
					
				
			} else {
				/*Caso o no removido é uma folha*/
				if(node.getDir()==null && node.getEsq()==null){
					System.out.println("Elemento removido!");
					node = null;				
				/*Caso o no removido tenha dois filhos*/
				} else if(node.getDir()!=null && node.getEsq()!=null){
					
					/*Faz a troca de dados, onde pega o menor dado da direita e troca com o dado do nó que será removido*/
					node.setValor(pegaMinimo(node.getDir()));
					
					/*Agora remove o dado que esta no novo Nó e depois recalcula a altura do no atual*/
					node.setDir(avlRemove(node.getDir(), node.getValor()));
					//Correção da altura
					node.setAltura(Maior_Altura(Altura_No(node.getEsq()), Altura_No(node.getDir())) + 1);
					
					if(FatorBalanceamento(node)<-1){
						if(valor < node.getEsq().getValor()){
							return rotacaoSimplesDireita(node);
						} else{
							return rotacaoDuplaDireita(node);
						}
					}
				}else{
					
					/*Caso o no tenha um filho a direita */
					if(node.getDir()!= null && node.getEsq()==null){
						
						aux = node.getDir();
						node = aux;
					/*ou a esquerda*/
					}else{
						aux = node.getEsq();
						node = aux;
					}	
					System.out.println("Elemento removido!");
				}
			}
			
			return node;

		}
		return null;
	}
	
	/*Função utilizada na remoção de nó pai que tem dois filhos, 
	 *onde pegará o valor do no que tem o menor valor a direita*/
	private int pegaMinimo(No node){
		if(node != null){
			if(node.getEsq()!=null){
				return pegaMinimo(node.getEsq());
			} else{
				return node.getValor();
			}
		}
		return 0; 
	}

	/*Busca*/
	public void avlQuery(No node, int valor){
		No busca = avlNoQuery(node, valor);
		if(busca!=null){
			System.out.println("Encontrado: " + node.getValor());
		}else{
			System.out.println("Não Encontrado");
		}
		
	}
	public No avlNoQuery(No node, int valor) {
		if(node!=null){
			if(valor == node.getValor()){
				return node;
			}
			else{
				if(valor < node.getValor()){
					return (avlNoQuery(node.getEsq(), valor));
				} else{
					return (avlNoQuery(node.getDir(), valor));
				}
			}
			
		} 
		return null;
	}
	
	//Limpa a Arvore AVL
	public void avlDestroy(No node){
		if(node!=null){
			node = null;
		}
	}
	
	/*Percorrer em arvore AVL - Pré-ordem*/
	public void avlPreOrdem(No node){
		if(node!=null){
			if(node==this.root){
				System.out.println("Valor: " + node.getValor() + " Fator: " + FatorBalanceamento(node) + "(RAIZ)");
			}else{
				System.out.println("Valor: " + node.getValor() + " Fator: " + FatorBalanceamento(node));
			}
			
			
			avlPreOrdem(node.getEsq());
			avlPreOrdem(node.getDir());
		}
	}
	
	/*Percorrer em arvore AVL - Em ordem ou Simétrica*/
	public void avlSimetrica(No node){
		if(node!=null){
			avlSimetrica(node.getEsq());
			if(node==this.root){
				System.out.println("Valor: " + node.getValor() + " Fator: " + FatorBalanceamento(node) + "(RAIZ)");
			}else{
				System.out.println("Valor: " + node.getValor() + " Fator: " + FatorBalanceamento(node));
			}
			
			avlSimetrica(node.getDir());
		}
	}
	
	/*Percorrer em arvore AVL - Pos-ordem*/
	public void avlPosOrdem(No node){
		if(node!=null){
			avlPosOrdem(node.getEsq());
			avlPosOrdem(node.getDir());
			if(node==this.root){
				System.out.println("Valor: " + node.getValor() + " Fator: " + FatorBalanceamento(node) + "(RAIZ)");
			}else{
				System.out.println("Valor: " + node.getValor() + " Fator: " + FatorBalanceamento(node));
			}
			
		}
	}
	/*Percorre a avl em ordem e joga seus elementos em um ArrayList*/
	public void avlPegaElemetos_Simetrico(No node, ArrayList <Integer> v){
		if(node!=null){
			avlPegaElemetos_Simetrico(node.getEsq(),v);
			v.add(node.getValor());
			avlPegaElemetos_Simetrico(node.getDir(), v);
		}
	}
	/*Percorre a avl em pre ordem e joga seus elementos em um ArrayList*/
	public void avlPegaElemetos_PreOrdem(No node, ArrayList <Integer> v){
		if(node!=null){
			v.add(node.getValor());
			avlPegaElemetos_PreOrdem(node.getEsq(), v);
			avlPegaElemetos_PreOrdem(node.getDir(), v);
		}
	}
	/*Percorre a avl em pos ordem e joga seus elementos em um ArrayList*/
	public void avlPegaElemetos_PosOrdem(No node, ArrayList <Integer> v){
		if(node!=null){
			avlPegaElemetos_PosOrdem(node.getEsq(), v);
			avlPegaElemetos_PosOrdem(node.getDir(), v);
			v.add(node.getValor());
		}
	}
	
	
	
	
}
