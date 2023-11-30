import greenfoot.*;
import java.util.HashMap;

/**
 * Uma classe que representa o placar em um mundo de jogo.
 * 
 * Autor: (seu nome)
 * Versão: (número da versão ou uma data)
 */
public class Placar extends World 
{
    // Flags para controlar o estado do jogo
    private boolean modoAutomatico;
    private boolean naoMostrou;
    private boolean ehIgual;
    
    // Imagem para o fundo do placar
    private GreenfootImage imagemPlacar;
    
    // HashMap para armazenar nomes de jogadores e seus tempos de sobrevivência
    private HashMap<String, Long> placarDesordenado;
    
    // Arrays para armazenar nomes de jogadores e tempos de sobrevivência ordenados
    private String[] nomeOrdenado;
    private long[] tempoOrdenado;
    
    // Tempo de sobrevivência do jogador
    private long tempoSobrevivido;

    /**
     * Construtor para objetos da classe Placar.
     */
    public Placar() 
    {
        super(800, 600, 1);
        naoMostrou = false;
        ehIgual = false;
        placarDesordenado = new HashMap<>();
        imagemPlacar = new GreenfootImage("placar.png");
        setBackground(imagemPlacar);
    }

    /**
     * Método responsável por ações contínuas enquanto o placar está ativo.
     */
    public void act()
    {
        if(naoMostrou)
        {
            mostrarPlacar();   
            naoMostrou = false;
        }
        restartGame();
    }

    /**
     * Método responsável por exibir o placar.
     */
    public void mostrarPlacar() 
    {
        setBackground(imagemPlacar);
        ordenarPlacar();
        int posicao = 1;
    
        // Define a cor do texto como branco
        Color corDoTexto = Color.WHITE;
        
        // Define a fonte e o tamanho
        Font fonte = new Font("Arial", 16);
    
        for (String nome : nomeOrdenado)
        {
            if(posicao <= 5)
            {
                // Configura a cor e a fonte antes de desenhar o texto
                getBackground().setColor(corDoTexto);
                getBackground().setFont(fonte);
        
                // Desenha o texto
                showText(posicao + "º", (getWidth() / 4 - 25), getHeight() / 3 + 30 + (posicao * 50));
                showText(nomeOrdenado[posicao - 1], (getWidth() / 3 + 20 ), getHeight() / 3 + 30 + (posicao * 50));
                showText(tempoOrdenado[posicao - 1] + " segundo(s)", (getWidth() - 300), getHeight() / 3 + 30 + (posicao * 50));
        
                posicao++;
            }
        }
        
        showText("Pressione R para voltar à sua aventura.", 400, 50);
    }

    /**
     * Método responsável por reiniciar o jogo.
     */
    private void restartGame()
    {
        Som.pararMusicaTema();
        if (Greenfoot.isKeyDown("r"))
        {
            Player player = new Player(modoAutomatico);
            Inimigo inimigo = new Inimigo(player);
            Magia magia = new Magia(player, 3, 40);
            inimigo.reiniciarInimigo();
            magia.reiniciarBolaDeFogo();
            Greenfoot.setWorld(new MeuMundo(modoAutomatico, this));
        }
    }

    /**
     * Método responsável por ordenar o placar usando o Bubble Sort.
     */
    private void ordenarPlacar()
    {
        String[] nomes = new String[placarDesordenado.size()];
        long[] tempos = new long[placarDesordenado.size()];
        int i = 0;
        for (String nome : placarDesordenado.keySet())
        {
            nomes[i] = nome;
            tempos[i] = placarDesordenado.get(nome);
            i++;
        }
        for (int j = 0; j < tempos.length; j++)
        {
            for (int k = 0; k < tempos.length - 1; k++)
            {
                if (tempos[k] < tempos[k + 1])
                {
                    // Troca as posições, se necessário
                    long aux = tempos[k];
                    tempos[k] = tempos[k + 1];
                    tempos[k + 1] = aux;
                    String aux2 = nomes[k];
                    nomes[k] = nomes[k + 1];
                    nomes[k + 1] = aux2;
                }
            }
        }

        // Copia os arrays ordenados
        nomeOrdenado = new String[nomes.length];
        tempoOrdenado = new long[tempos.length];
        
        for (int l = 0; l < nomes.length; l++)
        {
           nomeOrdenado[l]  = nomes[l];
           tempoOrdenado[l] = tempos[l];
        }
    }
    
    /**
     * Define as informações do jogador no placar.
     */
    public void setPlayerInfo(String nomePlayer, long tempoSobrevivido, boolean modoAutomatico)
    {
        this.modoAutomatico = modoAutomatico;
        
        // Verifica e ajusta o comprimento do nome do jogador
        if(nomePlayer.length() <= 1 || nomePlayer.length() > 8)
            nomePlayer = "default";
        
        // Verifica se o nome do jogador já existe
        for(String nome : placarDesordenado.keySet())
        {
            if(nomePlayer.equals(nome))
            {
                ehIgual = true;
                
                // Atualiza se o novo tempo de sobrevivência for maior
                if(tempoSobrevivido > placarDesordenado.get(nome))
                {
                    placarDesordenado.remove(nome, tempoSobrevivido);
                    placarDesordenado.put(nomePlayer, tempoSobrevivido);
                }  
            }
        }
        
        // Adiciona um novo jogador se ainda não estiver no placar
        if(!ehIgual)
            placarDesordenado.put(nomePlayer, tempoSobrevivido);
        
        ehIgual = false;
    }
    
    /**
     * Alterna a flag indicando se o placar está sendo exibido ou não.
     */
    public void mudarNaoMostrou()
    {
        naoMostrou = !naoMostrou;
    }
}
