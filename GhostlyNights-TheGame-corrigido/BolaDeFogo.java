import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Classe que cuida da movimentaçao e interaçao do ataque do player aos seus inimigos por meio de objetos BolaDeFogo.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class BolaDeFogo extends Magia
{   
    private GreenfootImage imagem;
    private int cooldown;

    
    /**
     * Construtor da classe BolaDeFogo
     * Define sua velocidade de movimento,
     * quanto de dano causa ao inimgo,
     * e seu sprite
     */
    public BolaDeFogo(Player player, int velocidade, int volume)
    {
        super(player, velocidade, volume);
        cooldown = 0;
        imagem = new GreenfootImage("magia1.png");
        imagem.scale(19, 12);
        setImage(imagem);
    }
    
    /**
     * Metodo que define o que ocorre quando um objeto BolaDeFogo se encontra
     * com um objeto Inimigo
     * Se existir um inimigo Inimigo com quem entra em contato:
     * causa dano ao inimigo e remove ele se sua vida ficar menor ou igual 
     */
    protected void colisaoInimigo()
    {
        super.colisaoInimigo();
        if (tocou())
        {
            getWorld().removeObject(this);
            alterarTocou();
        }
    }
}
