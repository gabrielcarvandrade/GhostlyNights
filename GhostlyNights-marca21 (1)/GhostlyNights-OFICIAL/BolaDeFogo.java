import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Classe que cuida da movimentaçao e interaçao do ataque do player aos seus inimigos por meio de objetos BolaDeFogo.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class BolaDeFogo extends Actor
{
    private int velocidade;
    private int dano;
    private int volume;
    
    private GreenfootImage imagem;
    private Player player;
    private BarraUlt barraUlt;

    
    /**
     * Construtor da classe BolaDeFogo
     * Define sua velocidade de movimento,
     * quanto de dano causa ao inimgo,
     * e seu sprite
     */
    public BolaDeFogo(Player player)
    {
        this.player = player;
        barraUlt = new BarraUlt(player);
        velocidade = 5;
        dano = 10;
        volume = 40;
        imagem = new GreenfootImage("magia1.png");
        imagem.scale(19, 12);
        setImage(imagem);
    }
    
    /**
     * Define a movimentaçao da bola de fogo no jogo
     * e chama o metodo colisaoInimigo se a bola de fogo nao estiver na borda do mundo
     */
    public void act()
    {
        move(velocidade);
        if (!isAtEdge())
            colisaoInimigo();
        else
            getWorld().removeObject(this);
    }
    
    /**
     * Metodo que define o que ocorre quando um objeto BolaDeFogo se encontra
     * com um objeto Inimigo
     * Se existir um inimigo Inimigo com quem entra em contato:
     * causa dano ao inimigo e remove ele se sua vida ficar menor ou igual 
     */
    private void colisaoInimigo()
    {
        Fantasma fantasma = (Fantasma) getOneIntersectingObject(Fantasma.class);
        if (fantasma != null)
        {
            fantasma.alterarVida(dano);
            fantasma.move(-20);
            int coordenadaX = fantasma.getX();
            int coordenadaY = fantasma.getY();
            if (fantasma.obterVida() <= 0)
            {
                getWorld().addObject(new EsmeraldaDeXp(), coordenadaX, coordenadaY);
                player.alterarAtributos();
                getWorld().removeObject(fantasma);
                
                Som.tocarDeathSound();
            }
            getWorld().removeObject(this);
        }
    }
    
    /**
     * Retorna o dano da bola de fogo
     */
    public int getDano()
    {
        return dano;
    }
    
    /**
     * Aumenta o dano da bola de fogo
     */
    public void aumentarDano()
    {
        dano += 3*player.getNivelPlayer();
    }
}
