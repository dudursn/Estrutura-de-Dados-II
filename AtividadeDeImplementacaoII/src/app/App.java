package app;
import arv_AVL.*;
import arv_RB.*;
import arv_B.*;


import java.util.ArrayList;

import java.util.Scanner;

public class App {

	/**
	 * Atividade Prática II
	 * Eduardo Roger
	 * Phillipe Mendonça 
	 */
	
	public static Scanner ler = new Scanner(System.in);
	public static AVL tVL = null; 
	public static ArvoreRB tRB = null;
	public static ArvoreB tBT = null;
	
	public static void main(String[] args) {
		
		String comando;
		do{
			System.out.println();
			System.out.println("Digite '0' para sair");
			System.out.println("Informe o comando: ");
			comando = ler.nextLine();

			if(!comando.equals("0")){
				operacoesArvores(comando);
			}
	
		}while(!comando.equals("0"));
		System.out.println("Programa encerrado");
	}
	
	
	public static void operacoesArvores (String str){
		
		/*Criação das Arvores ou limpeza*/
		if(str.equals("VL NEW")){
			if(tVL==null){
				tVL = new AVL();
				System.out.println("Arvore criada com sucesso");
			} else{
				tVL.avlDestroy(tVL.root);
				tVL = null;
				System.out.println("Arvore destruida com sucesso");
			}

		}else if(str.equals("RB NEW")){
			if(tRB==null){
				tRB = new ArvoreRB();
				System.out.println("Arvore criada com sucesso");
			} else{
				tRB.rbDestroy(tRB.root);
				tRB = null;
				System.out.println("Arvore destruida com sucesso");
			}	

		} else if(str.length() >6 && str.substring(0, 6).equals("BT NEW")){
			int grau  = Integer.parseInt(str.substring(7)); 
			if(tBT==null){
				if(grau >=2){
					tBT = new ArvoreB(grau);
					System.out.println("Arvore criada com sucesso");
				}else{
					System.err.println("Erro! Arvore so pode ser criada com grau maior que dois");
				}
			
			} else{
				tBT.btDestroy(tBT.root, grau);
				tBT = null;
				System.out.println("Arvore destruida com sucesso");
			}	
	
		/*Inserção nas Arvores - Elementos repetidos não são inseridos*/
		} else if(str.length()>5 && str.substring(0, 5).equals("VL I ")){
			
			if(tVL!=null){
				//A inserção funciona para varios valores quanto para apenas um valor
				String comando = str.substring(5);
				String valor[] = comando.split(",");
				for(int i = 0; i < valor.length; i++){
					
					int dado = Integer.parseInt(valor[i]);
					tVL.root = tVL.avlInsert(tVL.root, dado);
				}

			} else{
				System.err.println("Arvore vazia! Crie a arvore com o comando VL NEW");
			}

		}else if(str.length()>5 && str.substring(0, 5).equals("RB I ")){
			
			if(tRB!=null){
				//A inserção funciona para varios valores quanto para apenas um valor
				String comando = str.substring(5);
				String valor[] = comando.split(",");
				for(int i = 0; i < valor.length; i++){
					
					int dado = Integer.parseInt(valor[i]);
					tRB.rbInsert(tRB.root, dado);
				}
			

			} else{
				System.err.println("Arvore vazia!Crie a arvore com o comando RB NEW");

			}

		} else if(str.length()>5 && str.substring(0, 5).equals("BT I ")){
		 
		 	if(tBT!=null){
		 		//A inserção funciona para varios valores quanto para apenas um valor
				String comando = str.substring(5);
				String valor[] = comando.split(",");
				for(int i = 0; i < valor.length; i++){
					
					int dado = Integer.parseInt(valor[i]);
					tBT.btInsert(dado);
				}
				
			} else{
				System.err.println("Arvore vazia!Crie a arvore com o comando BT NEW");

			}

		/*Remoção nas Arvores*/
		} else if(str.length()>5 && str.substring(0, 5).equals("VL R ")){
			
			if(tVL!=null && tVL.root!=null){
				//A remoção funciona para varios valores quanto para apenas um valor
				String comando = str.substring(5);
				String valor[] = comando.split(",");
				for(int i = 0; i < valor.length; i++){
					
					int dado = Integer.parseInt(valor[i]);
					tVL.root = tVL.avlRemove(tVL.root, dado);
					
				}
				
			} else{
				System.err.println("Arvore vazia! Crie a arvore com o comando VL NEW ou insira elementos com o comando VL I");

			}
			
			
		}else if(str.length()>5 && str.substring(0, 5).equals("RB R ")){
			
			if(tRB!=null && tRB.root!=null){
				//A remoção funciona para varios valores quanto para apenas um valor
				String comando = str.substring(5);
				String valor[] = comando.split(",");
				for(int i = 0; i < valor.length; i++){
					
					int dado = Integer.parseInt(valor[i]);
					tRB.rbRemove(tRB.root, dado);
				}
				

			} else{
				System.err.println("Arvore vazia! Crie a arvore com o comando RB NEW ou insira elementos com o comando RB I");

			}

		} else if(str.length()>5 && str.substring(0, 5).equals("BT R ")){
			
			if(tBT!=null && tBT.root!=null){
				//A remoção funciona para varios valores quanto para apenas um valor
				String comando = str.substring(5);
				String valor[] = comando.split(",");
				for(int i = 0; i < valor.length; i++){
					
					int dado = Integer.parseInt(valor[i]);
					tBT.btRemove(dado);
					
				}
				
			} else{
				System.err.println("Arvore vazia!Crie a arvore com o comando BT NEW ou insira elementos com o comando BT I");

			}
			
		}
		/*Imprimir os dados em ordem*/
		else if(str.length()>9 && str.substring(0, 9).equals("PRINT IN ")){
			//Se for AVL
			if(str.contains("AVL")){
				if(tVL!= null && tVL.root!=null){
					tVL.avlSimetrica(tVL.root);
				} else{
					System.err.println("Arvore vazia!");
				}
			}
			//Se for RB
			else if(str.contains("RB")){
				if(tRB!= null && tRB.root!= tRB.nil){
					tRB.rbSimetrica(tRB.root);
				} else{
					System.err.println("Arvore vazia!");
				}
			//Imprime se for BT
			}else if(str.contains("BT")){
				if(tBT!= null && tBT.root!= null){
					tBT.btEm_ordem(tBT.root);
				} else{
					System.err.println("Arvore vazia!");
				}
			}else{
				System.err.println("Comando desconhecido");
				
			}
		/*Imprimir os dados em pre ordem*/		
		}else if(str.length()>10 && str.substring(0, 10).equals("PRINT PRE ")){
			//Se for AVL
			if(str.contains("AVL")){
				if(tVL!= null && tVL.root!=null){
					tVL.avlPreOrdem(tVL.root);
				} else{
					System.err.println("Arvore vazia!");
				}
			}
			//Se for RB
			else if(str.contains("RB")){
				if(tRB!= null && tRB.root!= tRB.nil){
					tRB.rbPreOrdem(tRB.root);
				} else{
					System.err.println("Arvore vazia!");
				}
			
			}else{
				System.err.println("Comando desconhecido");
				
			}
		/*Imprimir os dados em pos ordem*/		
		}else if(str.length()>10 && str.substring(0, 10).equals("PRINT POS ")){
			//Se for AVL
			if(str.contains("AVL")){
				if(tVL!= null && tVL.root!=null){
					tVL.avlPosOrdem(tVL.root);
				} else{
					System.err.println("Arvore vazia!");
				}
			}
			//Se for RB
			else if(str.contains("RB")){
				if(tRB!= null && tRB.root!= tRB.nil){
					tRB.rbPosOrdem(tRB.root);
				} else{
					System.err.println("Arvore vazia!");
				}
			
			}else{
				System.err.println("Comando desconhecido");
				
			}
		//Copia os elementos da AVL para RB ou B
		} else if (str.length()>11 && (str.substring(8,11).equals("AVL") || str.substring(9, 12).equals("AVL") )){
			if(tVL!=null && tVL.root !=null){
				//Copia os elementos da AVL para RB
				if((str.equals("COPY IN AVL RB") || str.equals("COPY PRE AVL RB") || str.equals("COPY POS AVL RB"))){
					if(tRB==null){
						//Se a RB não tenha sido criada ainda
						tRB = new ArvoreRB();
					}
					copiaAVLparaRB(str);
					//Para fazer a copia tem q criar a arvore avl
				}else if(str.length()>13 && (str.substring(0,14).equals("COPY IN AVL BT") || str.substring(0,15).equals("COPY PRE AVL BT") || str.substring(0,15).equals("COPY POS AVL BT"))){
					if(tBT==null){
						System.out.println("Crie a arvore com o comando BT NEW 'ordem' ");
					} else{
						copiaAVLparaBT(str);
					}
					
				}
				else{
					System.err.println("Comando desconhecido");
				}
			} else{
				System.err.println("Não é possível fazer a copia: Arvore vazia");
			}
			//Copia os elementos da RB para AVL ou B
		} else if(str.length()>10 && (str.substring(8,10).equals("RB") || str.substring(9,11).equals("RB"))){
			if(tRB!=null && tRB.root!=tRB.nil){
				if ((str.equals("COPY IN RB AVL") || str.equals("COPY PRE RB AVL") || str.equals("COPY POS RB AVL"))){
					if(tVL==null){
						tVL = new AVL();
					}
					copiaRBparaAVL(str);
				} else if(str.equals("COPY IN RB BT") || str.equals("COPY PRE RB BT") || str.equals("COPY POS RB BT")){
					if(tBT==null){
						System.out.println("Crie a arvore com o comando BT NEW 'ordem' ");
					} else{
						copiaRBparaBT(str);
					}
					
				}
				else {
					System.err.println("Comando desconhecido");
				}
			} else{
				System.err.println("Não é possível fazer a copia: Arvore vazia: Arvore vazia");
			}					
		}
		
		else{
			System.err.println("Comando desconhecido");
	
		}
			
	}
	
	public static void copiaAVLparaRB(String str){
		/*Os elementos da arvore AVL sao jogados em uma ArrayList e depois sao adicionados na arvore Rubro Negra*/
		int i;
		ArrayList <Integer> v;
		if(tVL.root!=null){
			if(str.length()>10){
				//Cria a ArrayList 
				v = new ArrayList<Integer>();
				if(str.substring(0,8).equals("COPY IN ")){
					/*Os elementos da ArrayList serao colocados em ordem*/
					tVL.avlPegaElemetos_Simetrico(tVL.root, v);
				} else if( str.substring(0,9).equals("COPY PRE ")){
					/*Os elementos da ArrayList serao colocados em pre ordem*/
					tVL.avlPegaElemetos_PreOrdem(tVL.root, v);	
				} else if(str.substring(0,9).equals("COPY POS ")){	
					/*Os elementos da ArrayList serao colocados em pos ordem*/
					tVL.avlPegaElemetos_PosOrdem(tVL.root, v);	
				}
				for(i=0; i < v.size(); i++){
					tRB.rbInsert(tRB.root, v.get(i));
				}
				//Remove todos os elementos da ArrayList
				v.clear();
			}
			
		}
	}
	
	public static void copiaAVLparaBT(String str){
	 //Os elementos da arvore AVL são jogados em uma ArrayList e depois são adicionados na arvore Rubro Negra
		int i;
		ArrayList <Integer> v;
		if(tVL.root!=null){
			if(str.length()>10){
				//Cria a ArrayList 
				v = new ArrayList<Integer>();
				if(str.substring(0,8).equals("COPY IN ")){
					//Os elementos da ArrayList serao colocados em ordem
					tVL.avlPegaElemetos_Simetrico(tVL.root, v);
				} else if( str.substring(0,9).equals("COPY PRE ")){
					//Os elementos da ArrayList serao colocados em pre ordem
					tVL.avlPegaElemetos_PreOrdem(tVL.root, v);	
				} else if(str.substring(0,9).equals("COPY POS ")){	
					//Os elementos da ArrayList serao colocados em pos ordem
					tVL.avlPegaElemetos_PosOrdem(tVL.root, v);	
				}
				for(i=0; i < v.size(); i++){
					tBT.btInsert(v.get(i));
				}
				//Remove todos os elementos da ArrayList
				v.clear();
			}
			
	
		} 
	}
	
	public static void copiaRBparaAVL(String str){
		/*Os elementos da arvore RB sao jogados em uma ArrayList e depois são adicionados na arvore AVL*/
		int i;
		ArrayList <Integer> v;
		if(tRB.root!=tRB.nil){
			if(str.length()>10){
				//Cria a ArrayList 
				v = new ArrayList<Integer>();
				if(str.substring(0,8).equals("COPY IN ")){
					/*Os elementos da ArrayList serao colocados em ordem*/
					tRB.rbPegaElemetos_Simetrico(tRB.root, v);
				} else if(str.substring(0,9).equals("COPY PRE ")){
					/*Os elementos da ArrayList sera£o colocados em pre ordem*/
					tRB.rbPegaElemetos_PreOrdem(tRB.root, v);	
				} else if(str.substring(0,9).equals("COPY POS ")){
					/*Os elementos da ArrayList serao colocados em pos ordem*/
					tRB.rbPegaElemetos_PosOrdem(tRB.root, v);	
				}
				for(i=0; i < v.size(); i++){
					tVL.root = tVL.avlInsert(tVL.root, v.get(i));
				}
				//Remove todos os elementos da ArrayList
				v.clear();
			}
		} 
	}
	public static void copiaRBparaBT(String str){
		/*Os elementos da arvore RB sao jogados em uma ArrayList e depois sao adicionados na arvore AVL*/
		int i;
		ArrayList <Integer> v;
		if(tRB.root!=tRB.nil){
			if(str.length()>10){
				//Cria a ArrayList 
				v = new ArrayList<Integer>();
				if(str.substring(0,8).equals("COPY IN ")){
					/*Os elementos da ArrayList serao colocados em ordem*/
					tRB.rbPegaElemetos_Simetrico(tRB.root, v);
				} else if(str.substring(0,9).equals("COPY PRE ")){
					/*Os elementos da ArrayList serao colocados em pre ordem*/
					tRB.rbPegaElemetos_PreOrdem(tRB.root, v);	
				} else if(str.substring(0,9).equals("COPY POS ")){
					/*Os elementos da ArrayList serao colocados em pos ordem*/
					tRB.rbPegaElemetos_PosOrdem(tRB.root, v);	
				}
				for(i=0; i < v.size(); i++){
					tBT.btInsert(v.get(i));
				}
				//Remove todos os elementos da ArrayList
				v.clear();
			}
		} 
	} 

}