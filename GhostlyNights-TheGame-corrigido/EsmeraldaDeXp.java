import greenfoot.*;

/**
 * Classe referente à esmeralda, objeto que da experiencia ao jogador.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class EsmeraldaDeXp extends Actor
{
    private GreenfootImage imagem;
    
    /**
     * Construtor da classe EsmeraldaDeXP
     */
    public EsmeraldaDeXp()
    {
        imagem = new GreenfootImage("Esmeralda.png");
        imagem.scale(20, 15);
        setImage(imagem);
    }
}
