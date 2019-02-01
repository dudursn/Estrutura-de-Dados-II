package arv_B;

public class ArvoreB implements BT_Interface{

    //Atributos da Classe ArvoreB
    public No root; //Atributo do Nó raiz;
    public int ordem; //Ordem da Arvore-B;
   

    //Construtor da Classe ArvoreB
    //Cria um novo nó para a raiz, seta a ordem passada como paâmetro e inicializa
    public ArvoreB(int n) {
        this.root = new No(n);
        this.ordem = n;
       
    }  

   

    //Metodo de Inserção na ArvoreB
    //parametros: key - chave a ser inserida
    public void btInsert(int key) {
        //Verifica se a chave a ser inserida existe
        if (btQuery(root, key) == null) { //só insere se não houver, para evitar duplicação de chaves
            //verifica se a chave está vazia
            if (root.getN() == 0) {
                root.getChave().set(0, key);//seta a chave na primeira posição da raiz
                root.setN(root.getN() + 1);
            } else { //caso nao estaja vazia
                No r = root;
                //verifica se a raiz está cheia
                if (r.getN() == ordem - 1) {//há necessidade de dividir a raiz
                    No s = new No(ordem);
                    root = s;
                    s.setFolha(false);
                    s.setN(0);
                    s.getFilho().set(0, r);
                    btDivideNo(s, 0, r);//divide nó
                    btInsereNoNaoCheio(s, key);//depois de dividir a raiz começa inserindo apartir da raiz

                } else {//caso contrario começa inserindo apartir da raiz
                    btInsereNoNaoCheio(r, key);
                }
            }
          System.out.println("Elemento Inserido");
        }
    }

    //Método de divisão de nó (split)
    //Parâmetros: x - nó Pai, y - nó Filho e i - índice i que indica que y é o i-ésimo filho de x.
    private void btDivideNo(No x, int i, No y) {
        int t = (int) Math.floor((ordem - 1) / 2);
        //cria nó z
        No z = new No(ordem);
        z.setFolha(y.isFolha());
        z.setN(t);

        //passa as t ultimas chaves de y para z
        for (int j = 0; j < t; j++) {
            if ((ordem - 1) % 2 == 0) {
                z.getChave().set(j, y.getChave().get(j + t));
            } else {
                z.getChave().set(j, y.getChave().get(j + t + 1));
            }
            y.setN(y.getN() - 1);
        }

        //se y nao for folha, pasa os t+1 últimos flhos de y para z
        if (!y.isFolha()) {
            for (int j = 0; j < t + 1; j++) {
                if ((ordem - 1) % 2 == 0) {
                    z.getFilho().set(j, y.getFilho().get(j + t));
                } else {
                    z.getFilho().set(j, y.getFilho().get(j + t + 1));
                }

            }
        }

        y.setN(t);//seta a nova quantidade de chaves de y

        //descola os filhos de x uma posição para a direita
        for (int j = x.getN(); j > i; j--) { 
            x.getFilho().set(j + 1, x.getFilho().get(j));
        }

        x.getFilho().set(i + 1, z);//seta z como filho de x na posição i+1

        //desloca as chaves de x uma posição para a direita, para podermos subir uma chave de y
        for (int j = x.getN(); j > i; j--) {
            x.getChave().set(j, x.getChave().get(j - 1));
        }

        //"sobe" uma chave de y para z
        if ((ordem - 1) % 2 == 0) {
            x.getChave().set(i, y.getChave().get(t - 1));
            y.setN(y.getN() - 1);
            
        } else {
            x.getChave().set(i, y.getChave().get(t));
        }

        //incrementa o numero de chaves de x
        x.setN(x.getN() + 1);

    }

    //Método para inserir uma chave em um nó não cheio
    //Paâmetros: x - nó a ser inserido, k - chave a ser inserida no nó x
    private void btInsereNoNaoCheio(No x, int k) {
        int i = x.getN() - 1;
        //verifica se x é um nó folha
        if (x.isFolha()) {
            //adquire a posição correta para ser inserido a chave
            while (i >= 0 && k < x.getChave().get(i)) {
                x.getChave().set(i + 1, x.getChave().get(i));
                i--;
            }
            i++;
            x.getChave().set(i, k);//insere a chave na posição i
            x.setN(x.getN() + 1);

        } else {//caso x não for folha
            //adquire a posição correta para ser inserido a chave
            while ((i >= 0 && k < x.getChave().get(i))) {
                i--;
            }
            i++;
            //se o filho i de x estiver cheio, divide o mesmo
            if ((x.getFilho().get(i)).getN() == ordem - 1) {
                btDivideNo(x, i, x.getFilho().get(i));
                if (k > x.getChave().get(i)) {
                    i++;
                }
            }
            //insere a chave no filho i de x
            btInsereNoNaoCheio(x.getFilho().get(i), k);
        }
        
    }

    //Método de busca de uma chave, retorna um nó onde a chave buscada se encontra
    //Parâmetros: X - nó por onde começar a busca, k - chave a ser buscada
    public No btQuery(No X, int k) {
        int i = 1;
        //procura ate nao estourar o tamanho e ate o valor nao ser maior q o procurado
        while ((i <= X.getN()) && (k > X.getChave().get(i - 1))) { 
            i++;
        }
        //se o tamanho nao tiver excedido e for o valor procurado..
        if ((i <= X.getN()) && (k == X.getChave().get(i - 1))) {
            return X;
        }
        //se nao foi é igual, entao foi o tamanho q excedeu. ai procura no filho se nao for folha
        if (X.isFolha()) { //se o no X for folha
            return null;
        } else {
            return (btQuery(X.getFilho().get(i - 1), k));
        }
    }

    //Método de Remoção de uma determinada chave da arvoreB
    public void btRemove(int key) {
        //verifica se a chave a ser removida existe
        if (btQuery(this.root, key) != null) {
            //N é o nó onde se encontra k
            No N = btQuery(this.root, key);
            int i = 1;

            //adquire a posição correta da chave em N
            while (N.getChave().get(i - 1) < key) {
                i++;
            }

            //se N for folha, remove ela e deve se balancear N
            if (N.isFolha()) {
                for (int j = i + 1; j <= N.getN(); j++) {
                    N.getChave().set(j - 2, N.getChave().get(j - 1));//desloca chaves quando tem mais de uma
                }
                N.setN(N.getN() - 1);
                if (N != this.root) {
                    btBalanceia_Folha(N);//Balanceia N
                }
            } else {//caso contrário(N nao é folha), substitui a chave por seu antecessor e balanceia a folha onde se encontrava o ancecessor
                No S = btAntecessor(this.root, key);//S eh onde se encontra o antecessor de k
                int y = S.getChave().get(S.getN() - 1);//y é o antecessor de k
                S.setN(S.getN() - 1);
                N.getChave().set(i - 1, y);//substitui a chave por y
                btBalanceia_Folha(S);//balanceia S
            }
            System.out.println("Elemento Removido");
        }
    }

    //Métode de Balancear um nó folha
    //Parâmetros: F - nó Folha a ser balanceada
    private void btBalanceia_Folha(No F) {
        //verifica se F está cheio
        if (F.getN() < Math.floor((ordem - 1) / 2)) {
            No P = btGetPai(root, F);//P é o pai de F
            int j = 1;

            //adquire a posição de F em P
            while (P.getFilho().get(j - 1) != F) {
                j++;
            }

            //verifica se o irmão à esquerda de F não tem chaves para emprestar
            if (j == 1 || (P.getFilho().get(j - 2)).getN() == Math.floor((ordem - 1) / 2)) {
                //verifica se o irmão à direita de F não tem chaves para emprestar
                if (j == P.getN() + 1 || (P.getFilho().get(j).getN() == Math.floor((ordem - 1) / 2))) {
                    btDiminui_Altura(F); //nenhum irmão tem chaves para emprestar eh necessario diminuir a altura
                } else {//caso contrario (tem chaves para emprestar) executa Balanceia_Dir_Esq
                    btBalanceia_Dir_Esq(P, j - 1, P.getFilho().get(j), F);
                }
            } else {//caso contrario (tem chaves para emprestar) executa Balanceia_Esq_Dir
                btBalanceia_Esq_Dir(P, j - 2, P.getFilho().get(j - 2), F);
            }
        }
    }

    //Método para diminuir a altura
    //Parâmetros: X - nó onde vai ser diminuido a altura
    private void btDiminui_Altura(No X) {
        int j;
        No P = new No(ordem);

        //verifica se X é a raiz
        if (X == this.root) {
            //verifica se X está vazio
            if (X.getN() == 0) {
                this.root = X.getFilho().get(0);//o filho o de x passa a ser raiz
                X.getFilho().set(0, null); // desaloca o filho de x
            }
        } else {//caso contrario(X nao é raiz)
            int t = (int) Math.floor((ordem - 1) / 2);
            //verifica se o numero de chaves de X é menor que o permitido
            if (X.getN() < t) {
                P = btGetPai(root, X);//P é pai de X
                j = 1;

                //adquire a posicao correta do filho X em P
                while (P.getFilho().get(j - 1) != X) {
                    j++;
                }

                //junta os nós
                if (j > 1) {
                    btJuncao_No(btGetPai(root, X), j - 1);
                } else {
                    btJuncao_No(btGetPai(root, X), j);
                }
                btDiminui_Altura(btGetPai(root, X));
            }
        }
    }
    
    //Mótodo de Balancear da esquerda para a direita
    //Parâmetros: P - Nó pai, e - indica que Esq é o e-ésimo filho de P, Esq - Nó da esquerda, Dir - Nó da direita
    private void btBalanceia_Esq_Dir(No P, int e, No Esq, No Dir) {
        //Desloca as chave de Dir uma posicao para a direita
        for (int i = 0; i < Dir.getN(); i++) {
            Dir.getChave().set(i + 1, Dir.getChave().get(i));
        }

        //Se Dir nao for folha descola seu filhos uma posicao para a direita
        if (!Dir.isFolha()) {//nao foi testado, mas teoricamente funciona
            for (int i = 0; i > Dir.getN(); i++) {
                Dir.getFilho().set(i + 1, Dir.getFilho().get(i));
            }
        }
        Dir.setN(Dir.getN() + 1);//Incrementa a quantidades de chaves em Dir
        Dir.getChave().set(0, P.getChave().get(e));//"desce" uma chave de P para Dir
        P.getChave().set(e, Esq.getChave().get(Esq.getN() - 1));//"sobe" uma chave de Esq para P
        Dir.getFilho().set(0, Esq.getFilho().get(Esq.getN()));//Seta o ultimo filho de Esq como primeiro filho de Dir
        Esq.setN(Esq.getN() - 1);//Decrementa a quantidade de chaves em Esq

    }

    //Método de Balancear da direita para a esquerda
    //Parâmetros: P - Nó pai, e - indica que Dir é o e-ésimo filho de P, Dir - Nó da direita, Esq - Nó da esquerda
    private void btBalanceia_Dir_Esq(No P, int e, No Dir, No Esq) {

        Esq.setN(Esq.getN() + 1);//Incrementa a quantidade de chaves em Esq
        Esq.getChave().set(Esq.getN() - 1, P.getChave().get(e));//"desce" uma chave de P para Esq
        P.getChave().set(e, Dir.getChave().get(0));//"sobe" uma chave de Dir para P
        Esq.getFilho().set(Esq.getN(), Dir.getFilho().get(0));//Seta o primeiro filho de Dir como último filho de Esq

        //descola as chaves de Dir uma posição para a esquerda
        for (int j = 1; j < Dir.getN(); j++) {
            Dir.getChave().set(j - 1, Dir.getChave().get(j));
        }

        //se Dir nao for folha, desloca todos os filhos de Dir uma posicao a esquerda
        if (!Dir.isFolha()) {//nao foi testado, mas teoricamente funciona
            for (int i = 1; i < Dir.getN()+1 ; i++) {
                Dir.getFilho().set(i - 1, Dir.getFilho().get(i));
            }
        }

        //descrementa a quantidade de chaves de Dir
        Dir.setN(Dir.getN() - 1);

    }

    //Método para junção do nó
    //Parâmetros: X - No pai, i - posicao do filho de X onde vai ser juntado
    private void btJuncao_No(No X, int i) {
        No Y = X.getFilho().get(i - 1); //cria Y
        No Z = X.getFilho().get(i);//cria Z

        int k = Y.getN();
        Y.getChave().set(k, X.getChave().get(i - 1));//"desce" uma chave de X para X

        //descola as de chaves de Z para Y
        for (int j = 1; j <= Z.getN(); j++) {
            Y.getChave().set(j + k, Z.getChave().get(j - 1));
        }

        //se Z nao for folha, descola seus filhos tbm
        if (!Z.isFolha()) {
            for (int j = 1; j <= Z.getN(); j++) {
                Y.getFilho().set(j + k, Z.getFilho().get(j - 1));
            }
        }

        //seta a quantidades de chaves em Y
        Y.setN(Y.getN() + Z.getN() + 1);
        
        X.getFilho().set(i, null);//desaloca o Z setando o filho de X que apontava pra Z pra null

        //descola os filhos e as chaves de X uma para a esquera, para "fechar o buraco"
        for (int j = i; j <= X.getN() - 1; j++) {//ainda nao
            X.getChave().set(j - 1, X.getChave().get(j));
            X.getFilho().set(j, X.getFilho().get(j + 1));
        }

        //decrementa a quantidade de chaves em X
        X.setN(X.getN() - 1);
    }

    //Metodo que retorna o nó onde a chave antecessora de K se encontra
    //Parâmetros: N - Nó onde começa a busca, key - chave a ser buscada
    private No btAntecessor(No N, int key) {
        int i = 1;
        while (i <= N.getN() && N.getChave().get(i - 1) < key) {
            i++;
        }
        if (N.isFolha()) {
            return N;
        } else {
            return btAntecessor(N.getFilho().get(i - 1), key);
        }
    }

    //Metodo que retorna o nó pai de N
    //Parâmetros: T - Nó onde começa a busca, N - nó que deve se buscar o pai
    private No btGetPai(No T, No N) {
        if (this.root == N) {
            return null;
        }
        for (int j = 0; j <= T.getN(); j++) {
            if (T.getFilho().get(j) == N) {
                return T;
            }
            if (!T.getFilho().get(j).isFolha()) {
                No X = btGetPai(T.getFilho().get(j), N);
                if (X != null) {
                    return X;
                }
            }
        }
        return null;
    }

    //Método para Limpar a arvoreB.
    //Parâmetros: N - Nó onde se deve iniciar a varredura, ordem - Nova ordem da arvoreB
    public void btDestroy(No N, int ordem) {

        for (int i = 0; i < N.getN() + 1; i++) {
            if (!N.isFolha()) {
                btDestroy(N.getFilho().get(i), ordem);
            }
            N.getFilho().set(i, null);
            N.setN(0);
        }

        if (N == this.root) {
            this.root = new No(ordem);
        }
 
    }
    
    
    //Método para Imprimir os dados da arvoreB em ordem.
	public void btEm_ordem(No node){
		int i;
		if (node != null){
			if(node==this.root){
				for (i = 0; i < node.getN(); i++){
					btEm_ordem(node.getFilho().get(i));
					if(node==this.root){
						System.out.println(node.getChave().get(i) + " (RAIZ)");
					} else{
						System.out.println(node.getChave().get(i));
					}
				}
			} else{
				for (i = 0; i < node.getN(); i++){
					btEm_ordem(node.getFilho().get(i));
					System.out.println(node.getChave().get(i));
				}
			}
			btEm_ordem(node.getFilho().get(i));
		}
	}
	
}
