import greenfoot.*;
import java.util.HashMap;
/**
 * Write a description of class Placar here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Placar extends World 
{
    private String nomePlayer;
    private long tempoSobrevivido;
    
    private boolean modoAutomatico;
    private boolean naoMostrou;
    private boolean ehIgual;
    
    private GreenfootImage imagemPlacar;
    private HashMap<String, Long> placarDesordenado;
    private String[] nomeOrdenado;
    private long[] tempoOrdenado;

    /**
     * Constructor for objects of class Placar.
     * 
     */
    public Placar() 
    {
        super(800, 600, 1);
        this.modoAutomatico = modoAutomatico;
        naoMostrou = false;
        ehIgual = false;
        placarDesordenado = new HashMap<>();
        imagemPlacar = new GreenfootImage("placar.png");
        setBackground(imagemPlacar);
    }

    /**
     * Método responsável por cuidar do que deve acontecer enquanto o placar estiver rodando.
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
     * Método responsável por mostrar o placar.
     */
    public void mostrarPlacar() 
    {
        setBackground(imagemPlacar);
        ordenarPlacar();
        int posicao = 1;
    
        // Define cor do texto como branco
        Color corDoTexto = Color.WHITE;
        
        // Define fonte e tamanho
        Font fonte = new Font("Arial", 16);
    
        for (String nome : nomeOrdenado)
        {
            if(posicao<=5)
            {
                // Configura a cor e fonte antes de desenhar o texto
                getBackground().setColor(corDoTexto);
                getBackground().setFont(fonte);
        
                // Desenha o texto
                showText(posicao + "º", (getWidth() / 4 - 25), getHeight() / 3 + 30 + (posicao * 50));
                showText(nomeOrdenado[posicao-1], (getWidth() / 3 + 20 ), getHeight() / 3 + 30 + (posicao * 50));
                showText(tempoOrdenado[posicao-1] + " segundo(s)", (getWidth() - 300), getHeight() / 3 + 30 + (posicao * 50));
        
                posicao++;
            }
        }
        
        showText("Aperte R para voltar à sua aventura.", 400, 50);
    }


    /**
     * Método responsável pela função de reiniciar o jogo.
     */
    private void restartGame()
    {
        Som.pararMusicaTema();
        if (Greenfoot.isKeyDown("r"))
        {
            Player player = new Player(modoAutomatico);
            Inimigo inimigo = new Inimigo(player);
            BolaDeFogo magia = new BolaDeFogo(player, 3, 40);
            inimigo.reiniciarInimigo();
            magia.reiniciarBolaDeFogo();
            Greenfoot.setWorld(new MeuMundo(modoAutomatico, this));
        }
    }

    /*
     * Método responsável por ordenar o placar por meio de um Bubble Sort.
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
                    long aux = tempos[k];
                    tempos[k] = tempos[k + 1];
                    tempos[k + 1] = aux;
                    String aux2 = nomes[k];
                    nomes[k] = nomes[k + 1];
                    nomes[k + 1] = aux2;
                }
            }
        }

        nomeOrdenado = new String[nomes.length];
        tempoOrdenado = new long[tempos.length];
        
        for (int l = 0; l < nomes.length; l++)
        {
           nomeOrdenado[l]  = nomes[l];
           tempoOrdenado[l] = tempos[l];
        }
    }
    
    public void setPlayerInfo(String nomePlayer, long tempoSobrevivido)
    {
        if(nomePlayer.length() <=1 || nomePlayer.length()>8)
        nomePlayer = "default";
        for(String nome : placarDesordenado.keySet())
        {
            if(nomePlayer.equals(nome))
            {
                ehIgual = true;
                if(tempoSobrevivido > placarDesordenado.get(nome))
                {
                    placarDesordenado.remove(nome, tempoSobrevivido);
                    placarDesordenado.put(nomePlayer, tempoSobrevivido);
                }  
            }
        }
        if(!ehIgual)
        placarDesordenado.put(nomePlayer, tempoSobrevivido);
        ehIgual = false;
    }
    
    public void mudarNaoMostrou()
    {
        naoMostrou = !naoMostrou;
    }
}
