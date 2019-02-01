package arv_RB;

import java.util.ArrayList;

/*Implementação da Árvore Rubro Negra*/

public class ArvoreRB implements ArvRB, RB_Interface{
	
	//Root é a raiz e nil são os nós folhas, os quais todos são pretos e nil um nó ficticio
	public No root, nil;
	
	//Contrutor da RB
	public ArvoreRB(){
		nil = new No();
		root = nil;
	};
	

	//Inserção
	public void rbInsert(No node, int valor){
		//y e x são os nós auxiliares que ajudarão ao percorrer a arvore
		No anterior, atual;
		anterior = nil;
		atual = node;
		//Percorre a arvore e verifica onde o nó que contém o valor inserido será colocado
		while(atual!=nil){
			anterior = atual;
			if(valor < atual.getValor()){
				atual = atual.getEsq();
			} else{
				atual = atual.getDir();
			}
		}
		//O nó é criado
		No newNode = new No (valor);
		//O pai é o anterior
		newNode.setPai(anterior);
		//Se o anterior que é o pai for nil, então o nó inserido será a raiz da arvore
		if(anterior == nil){
			root = newNode;
		//Senão, o pai é um nó e passará a apontar para o nó criado
		} else if(newNode.getValor() < anterior.getValor()){
			anterior.setEsq(newNode);
		}else{
			anterior.setDir(newNode);
		}
		//Seta a esquerda e a direita do nó inserido com nil
		newNode.setEsq(nil);
		newNode.setDir(nil);
		//Todo nó inserido é vermelho
		newNode.setCor(ArvRB.RED);
		//Faz a correção das cores
		rbInsertFixup(newNode);
		System.out.println("Elemento Inserido");
			
	}
	//Faz a correção das cores enquanto o nó pai é vermelho e o nó critico é diferente da raiz
	private void rbInsertFixup(No z){
		//Verifica se a cor do No pai é vermelho
		while(z != root && z.getPai().getCor() == RED) {
			//verifica se o No pai de z é filho esquerdo do Nó avô de z
			if (z.getPai() == z.getPai().getPai().getEsq()) {
				//O No auxiliar Y é recebido com o No tio de z
                No y = z.getPai().getPai().getDir();
              //Verifica se o tio é vermelho, se sim trocam as cores dos Nos(Caso 1):
                if (y.getCor() == RED) {
                	//Pai
                    z.getPai().setCor(ArvRB.BLACK);
                    //Tio
                    y.setCor(ArvRB.BLACK);
                    //Avô
                    z.getPai().getPai().setCor(ArvRB.RED);
                    //No z passa a ser o nó avô
                    z = z.getPai().getPai();
                } else {
                	//Se não, é porque o tio é preto, então se o No z é filho direito de No pai de z(Caso 2)
                    if (z == z.getPai().getDir()) {
                    	//z passa a ser o no pai
                        z = z.getPai();
                      //E a arvore sofre rotação a esquerda
                        rbRotacaoAEsquerda(z);
                    }
                    //O pai de z fica preto
                    z.getPai().setCor(ArvRB.BLACK);
                    //O avô de z fica vermelho
                    z.getPai().getPai().setCor(ArvRB.RED);
                    //E a arvore sofre a rotação a direita
                    rbRotacaoADireita(z.getPai().getPai());
                }
            } else {
            	//verifica se o No pai de z é filho direito do Nó avô de z
                if (z.getPai() == z.getPai().getPai().getDir()) {
                	//O No auxiliar Y é recebido com o No tio de z
                    No y = z.getPai().getPai().getEsq();
                    //Verifica se o tio é vermelho, se sim trocam as cores dos Nos(Caso 1):
                    if (y.getCor() == ArvRB.RED) {
                    	//Pai
                        z.getPai().setCor(ArvRB.BLACK);
                        //Tio
                        y.setCor(ArvRB.BLACK);
                        //Avô
                        z.getPai().getPai().setCor(ArvRB.RED);
                        z = z.getPai().getPai();
                    } else {
                    	//Se não, é porque o tio é preto, então se o No z é filho esquerdo de No pai de z (Caso 3)
                        if (z == z.getPai().getEsq()) {
                        	//z se torna o pai
                            z = z.getPai();
                            //E a arvore sofre rotação a direita
                            rbRotacaoADireita(z);
                        }
                        //O pai de z fica preto
                        z.getPai().setCor(ArvRB.BLACK);
                        //O avô de z fica vermelho
                        z.getPai().getPai().setCor(ArvRB.RED);
                      //E a arvore sofre rotação a esquerda
                        rbRotacaoAEsquerda(z.getPai().getPai());
                    }
                }
            }
        }
		//A raiz é setada com a cor preta
		root.setCor(ArvRB.BLACK);
	}
	
	
	/*Remoção*/
	public void rbRemove(No node, int valor){
		if(node!=nil){
			//Faz a busca pelo valor a ser removido e retorno o nó
			No z = rbQuery(node, valor, 0);
			//Se foi encontrado inicia-se a remoção
			if(z!= null){
				z = rbRemove(z);
				System.out.println("Elemento Removido: " + z.getValor());
			}
			//Senão 
		} else{
			System.out.println("Elemento Não Removido!");
		}
	}
	
	//Remove de fato o nó 
	private No rbRemove(No z){
		
		No y, x, aux;
		//y vai ser o nó removido no final, x será o nó que ficará critico, aux ajudará a percorrer a árvore
		
        //Se o nó a ser removido for uma folha
        if (z.getEsq() == nil || z.getDir() == nil) {
            y = z;
         //Então ele tem filhos, caso não entre na primeira condição
        } else {
        	//aux recebe o filho direito de z
        	aux = z.getDir();
        	//Enquanto aux tiver filho a esquerda, aux vai recebendo seu filho a esquerda
            while (aux.getEsq() != nil) {
                aux = aux.getEsq();
            }
            //y recebe o filho a direita de z, caso aux não tenha filhos a esquerda, se tiver, y recebe o nó mais a esquerda da subarvore que começa com o nó aux
            y = aux;
        }
        //Caso o Nó y tenha um filho não nulo
        if (y.getEsq() != nil) {
            x = y.getEsq();
        } else {
            x = y.getDir();
        }
        
        x.setPai(y.getPai());
        
        //Se pai for nil, x passa a ser root
        if (y.getPai() == nil) {
            root = x;
        } else {
            if (y == y.getPai().getEsq()) {
                y.getPai().setEsq(x);
            } else {
            	y.getPai().setDir(x);
            }
        }
        //Coloca o valor de y no Nó z
        if (y != z) {
            z.setValor(y.getValor());
        }
        //Se o no removido for preto, temos problema
        if (y.getCor() == ArvRB.BLACK) {
            rbRemoveFixup(x);
        }
        return y;
    }

	private void rbRemoveFixup(No x){
		
		No w;
		//Só dar problema se o nó x for preto
	    while (x != root && x.getCor() == ArvRB.BLACK) {
	    	//Verifica se o No x é filho esquerdo
	    	if (x == x.getPai().getEsq()) {
	    		//w recebe o No irmão de x, no caso o direito
	    		w = x.getPai().getDir();
	    		//Se a cor do irmão é vermelho
	    		if (w.getCor() == ArvRB.RED) {
	    			//O No irmão passa a ser preto
	    			w.setCor(ArvRB.BLACK);
	    			//O nó pai passa a ser vermelho
	                x.getPai().setCor(ArvRB.RED);
	                //Rotação a esquerda do nó pai
	                rbRotacaoAEsquerda(x.getPai());
	                w = x.getPai().getDir();
	            }
	            if (w.getEsq().getCor() == ArvRB.BLACK && w.getDir().getCor() == ArvRB.BLACK) {
	                w.setCor(ArvRB.RED);
	                x = x.getPai();
	            } else {
	                if (w.getDir().getCor() == ArvRB.BLACK) {
	                	w.getEsq().setCor(ArvRB.BLACK);
	                    w.setCor(ArvRB.RED);
	                    rbRotacaoADireita(w);
	                    w = x.getPai().getDir();
	                }
	                w.setCor(x.getPai().getCor());
	                x.getPai().setCor(ArvRB.BLACK);
	                w.getDir().setCor(ArvRB.BLACK);
	                rbRotacaoAEsquerda(x.getPai());
	                x = root;
	            }
	    	} else {
	    		w = x.getPai().getEsq();
	            if (w.getCor() == ArvRB.RED) {
	            	w.setCor(ArvRB.BLACK);
	                x.getPai().setCor(ArvRB.RED);
	                rbRotacaoADireita(x.getPai());
	                w = x.getPai().getEsq();
	            }
	            if (w.getDir().getCor() == ArvRB.BLACK && w.getEsq().getCor() == ArvRB.BLACK) {
	            	w.setCor(ArvRB.RED);
	            	x = x.getPai();
	            } else {
	                if (w.getEsq().getCor() == ArvRB.BLACK) {
	                	w.getDir().setCor(ArvRB.BLACK);
	                   	w.setCor(ArvRB.RED);
	                   	rbRotacaoAEsquerda(w);
	                   	w = x.getPai().getEsq();
	                }
	                w.setCor(x.getPai().getCor());
	                x.getPai().setCor(ArvRB.BLACK);
	                w.getEsq().setCor(ArvRB.BLACK);
	                rbRotacaoADireita(x.getPai());
	                x = root;
	            }
	    	}
	    }
	    root.setCor(ArvRB.BLACK);
	    
	}
		
	/*Busca*/
	public No rbQuery(No node, int valor, int estado) {
		if(node!=null){
			if(valor == node.getValor()){
				return node;
			}
			else{
				if(valor < node.getValor()){
					return (rbQuery(node.getEsq(), valor, 0));
				} else{
					return (rbQuery(node.getDir(), valor, 0));
				}
			}
			
		} 
		return null;
	}
	//Limpa a Arvore RB
	public void rbDestroy(No node){
		if(node!=nil){
			node = nil;
		}
	}
	
	//Rotação a direita na Rubro Negra
	private void rbRotacaoADireita(No node){
				
		No aux;
		//aux recebe o nó filho esquerdo do no critico Node
		aux = node.getEsq();
		//O filho esquerdo do nó critico passa a ser o filho direito do aux
		node.setEsq(aux.getDir());
		//Se o filho direito de aux existe
		if(aux.getDir()!=nil){
			//O pai passa a ser o nó critico
			aux.getDir().setPai(node);
		}
		//O pai do aux passa ser o pai do nó critico node
		aux.setPai(node.getPai());
		//Se o pai não existe, é porque o nó é a raiz
		if(node.getPai()==nil){
			root = aux;				
		}else{
			//Senão, aux passa a ser filho do pai do nó critico
			if(node == node.getPai().getDir()){
				node.getPai().setDir(aux);
			}else{
				node.getPai().setEsq(aux);
			}
		}
		//O filho dirito de aux é o nó critico
		aux.setDir(node);
		//E o pai do no critico é o aux
		node.setPai(aux);
				
		}
			
		//Rotação a esquerda na Rubro Negra
		private void rbRotacaoAEsquerda(No node){
					
			No aux;
			//aux recebe o nó filho direito do no critico Node
			aux = node.getDir();
			//O filho edireitodo nó critico passa a ser o filho esquerdo do aux
			node.setDir(aux.getEsq());
			//Se o filho esquerdo de aux existe
			if(aux.getEsq()!=nil){
				//O pai passa a ser o nó critico
				aux.getEsq().setPai(node);
			}
			//O pai do aux passa ser o pai do nó critico node
			aux.setPai(node.getPai());
			//Se o pai não existe, é porque o nó é a raiz
			if(node.getPai()==nil){
				root = aux;				
			}else{
				//Se o pai não existe, é porque o nó é a raiz
				if(node == node.getPai().getEsq()){
					node.getPai().setEsq(aux);
				}else{
					node.getPai().setDir(aux);
				}
			}
			//O filho dirito de aux é o nó critico
			aux.setEsq(node);
			//E o pai do no critico é o aux
			node.setPai(aux);
		}
				
		/*Percorrer em arvore RB - Pré-ordem*/
		public void rbPreOrdem(No node){
			String color;
			if(node!=nil){
				if(node.getCor() == 1){
					color = "vermelho";
				} else{
					color = "preto";
				}
				if(node==this.root){
					System.out.println("Valor: " + node.getValor() + " Cor: " + color + "(RAIZ)");
				}else{
					System.out.println("Valor: " + node.getValor() + " Cor: " + color);
				}
				
				rbPreOrdem(node.getEsq());
				rbPreOrdem(node.getDir());
			}
		}
		
		/*Percorrer em arvore RB - Em ordem ou Simétrica*/
		public void rbSimetrica(No node){
			String color;
			if(node!=nil){
				rbSimetrica(node.getEsq());
				if(node.getCor() == 1){
					color = "vermelho";
				} else{
					color = "preto";
				}
				if(node==this.root){
					System.out.println("Valor: " + node.getValor() + " Cor: " + color + "(RAIZ)");
				}else{
					System.out.println("Valor: " + node.getValor() + " Cor: " + color);
				}
				
				rbSimetrica(node.getDir());
			}
		}
		
		/*Percorrer em arvore RB - Pos-ordem*/
		public void rbPosOrdem(No node){
			String color;
			if(node!=nil){
				rbPosOrdem(node.getEsq());
				rbPosOrdem(node.getDir());
				if(node.getCor() == 1){
					color = "vermelho";
				} else{
					color = "preto";
				}
				if(node==this.root){
					System.out.println("Valor: " + node.getValor() + " Cor: " + color + "(RAIZ)");
				}else{
					System.out.println("Valor: " + node.getValor() + " Cor: " + color);
				}
				
			}
		}
		
		public void rbPegaElemetos_Simetrico(No node, ArrayList <Integer> v){
			if(node!=nil){
				rbPegaElemetos_Simetrico(node.getEsq(), v);
				v.add(node.getValor());
				rbPegaElemetos_Simetrico(node.getDir(), v);
			}
		}
		/*Percorre a avl em pre ordem e joga seus elementos em um ArrayList*/
		public void rbPegaElemetos_PreOrdem(No node, ArrayList <Integer> v){
			if(node!=nil){
				v.add(node.getValor());
				rbPegaElemetos_PreOrdem(node.getEsq(), v);
				rbPegaElemetos_PreOrdem(node.getDir(), v);
			}
		}
		/*Percorre a avl em pos ordem e joga seus elementos em um ArrayList*/
		public void rbPegaElemetos_PosOrdem(No node, ArrayList <Integer> v){
			if(node!=nil){
				rbPegaElemetos_PosOrdem(node.getEsq(), v);
				rbPegaElemetos_PosOrdem(node.getDir(), v);
				v.add(node.getValor());
			}
		}
}
