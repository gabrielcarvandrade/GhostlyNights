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

    
    /**
     * Construtor da classe BolaDeFogo
     * Define sua velocidade de movimento,
     * quanto de dano causa ao inimgo,
     * e seu sprite
     */
    public BolaDeFogo(Player player, int velocidade, int volume)
    {
        super(player, velocidade, volume);
        dano = getDano();
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
    private void colisaoInimigo()
    {
        Inimigo inimigo = (Inimigo) getOneIntersectingObject(Inimigo.class);
        if (inimigo != null)
        {
            inimigo.alterarVida(dano);
            inimigo.move(-20);
            int coordenadaX = inimigo.getX();
            int coordenadaY = inimigo.getY();
            if (inimigo.obterVida() <= 0)
            {
                getWorld().addObject(new EsmeraldaDeXp(), coordenadaX, coordenadaY);
                player.alterarAtributos();
                getWorld().removeObject(inimigo);
                
                Som.tocarDeathSound();
            }
            getWorld().removeObject(this);
        }
    }
}
