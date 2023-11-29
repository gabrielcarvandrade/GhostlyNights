import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Classe que cuida da movimentaçao e interaçao do ataque do player aos seus inimigos por meio de objetos BolaDeFogo.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class Magia extends Actor
{
    private int velocidade;
    private static int dano = 10;
    private int volume;
    private boolean tocou;
    
    private GreenfootImage imagem;
    private Player player;

    
    /**
     * Construtor da classe BolaDeFogo
     * Define sua velocidade de movimento,
     * quanto de dano causa ao inimgo,
     * e seu sprite
     */
    public Magia(Player player, int velocidade, int volume)
    {
        this.player = player;
        this.velocidade = velocidade;
        this.volume = volume;
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
    protected void colisaoInimigo()
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
            tocou = true;
        }
    }
    
    /**
     * Metodo que retorna se a magia encostou no inimigo
     */
    public boolean tocou()
    {
        return tocou;
    }
    
    /**
     * ALtera o valor do booleano tocou para evitar da magia dar dano a mais no inimigo quando se encontrarem
     */
    public void alterarTocou()
    {
        tocou = false;
    }
    
    /**
     * Retorna o dano da magia
     */
    public static int getDano()
    {
        return dano;
    }
    
    /**
     * Retorna a velocidade da magia
     */
    public int getVelocidade()
    {
        return velocidade;
    }
    
    /**
     * Retorna o player
     */ 
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Aumenta o dano da magia de fogo
     */
    public static void aumentarDano()
    {
        dano += 3;
    }
    
    /**
     * Metodo para reiniciar o dano da magia
     */
    public void reiniciarBolaDeFogo()
    {
        dano = 10;
    }
}
