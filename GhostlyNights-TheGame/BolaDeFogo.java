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
    private static int dano = 10;
    private int volume;
    
    private GreenfootImage imagem;
    private Player player;

    
    /**
     * Construtor da classe BolaDeFogo
     * Define sua velocidade de movimento,
     * quanto de dano causa ao inimgo,
     * e seu sprite
     */
    public BolaDeFogo(Player player, int velocidade, int volume)
    {
        this.player = player;
        this.velocidade = velocidade;
        dano = getDano();
        this.volume = volume;
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
    
    /**
     * Retorna o dano da bola de fogo
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
     * Aumenta o dano da bola de fogo
     */
    public static void aumentarDano()
    {
        dano += 3;
    }
    
    /**
     * Metodo para reiniciar o dano da bola de fogo
     */
    public void reiniciarBolaDeFogo()
    {
        dano = 10;
    }
}
