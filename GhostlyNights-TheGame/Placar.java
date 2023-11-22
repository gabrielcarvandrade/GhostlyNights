import greenfoot.*;
import java.util.TreeMap;
/**
 * Write a description of class Placar here.
 * 
 * @author (your name)
 * @version (a version number or a date)
 */
public class Placar extends World 
{
    private String unidadeDeTempo;
    
    GreenfootImage imagemPlacar;
    MeuMundo mundo;
    TreeMap<String, Long> placarOrdenado;

    /**
     * Constructor for objects of class Placar.
     * 
     */
    public Placar(String nomePlayer, Long tempoSobrevivido) 
    {
        super(800, 600, 1);
        mundo = new MeuMundo(true);
        placarOrdenado = new TreeMap<>();
        imagemPlacar = new GreenfootImage("placar.png");
        setBackground(imagemPlacar);
        placarOrdenado.put(nomePlayer, tempoSobrevivido);
        mostrarPlacar();
    }

    /**
     * Método responsável por cuidar do que deve acontecer enquanto o placar estiver rodando.
     */
    public void act()
    {
        restartGame();
    }

    /**
     * Método responsável por mostrar o placar.
     */
    public void mostrarPlacar() 
    {
        int posicao = 1;
    
        // Define cor do texto como branco
        Color corDoTexto = Color.WHITE;
        
        // Define fonte e tamanho
        Font fonte = new Font("Gothic", 16);
    
        for (String nome : placarOrdenado.keySet())
        {
            long tempo = placarOrdenado.get(nome);
            String textoPosicao = posicao + "";
            
            // Configura a cor e fonte antes de desenhar o texto
            getBackground().setColor(corDoTexto);
            getBackground().setFont(fonte);
    
            getBackground().drawString(textoPosicao, (getWidth() / 4 - 25), getHeight() / 3 + 35 + (posicao * 50));
            getBackground().drawString(nome, (getWidth() / 3 - 35), getHeight() / 3 + 35 + (posicao * 50));
            getBackground().drawString(tempo + " segundo(s)", (getWidth() - 340), getHeight() / 3 + 35 + (posicao * 50));
    
            posicao++;
        }
    }


    /**
     * Método responsável por reiniciar o jogo.
     */
    private void restartGame()
    {
        if (Greenfoot.isKeyDown("r"))
        {
            Greenfoot.setWorld(new MeuMundo(true));
        }
    }
}
