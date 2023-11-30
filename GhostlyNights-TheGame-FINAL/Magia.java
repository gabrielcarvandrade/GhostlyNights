import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Classe responsável pela movimentação e interação do ataque do jogador com seus inimigos por meio de objetos BolaDeFogo.
 * 
 * Autores: Gabriel Carvalho, Alexandre Carvalhaes, Douglas Silves, Ayron Sanfra. 
 * Versão: Marca 20.0
 */
public class Magia extends Actor
{
    // Atributos da magia
    private int velocidade;
    private static int dano = 10;
    private int volume;
    private boolean tocou;
    
    // Imagem da magia
    private GreenfootImage imagem;
    private Player player;

    
    /**
     * Construtor da classe BolaDeFogo.
     * Define sua velocidade de movimento, quanto de dano causa ao inimigo e seu sprite.
     * @param player Referência ao jogador associado à magia.
     * @param velocidade Velocidade de movimento da magia.
     * @param volume Volume do som da magia.
     */
    public Magia(Player player, int velocidade, int volume)
    {
        this.player = player;
        this.velocidade = velocidade;
        this.volume = volume;
    }
    
    /**
     * Define a movimentação da bola de fogo no jogo e chama o método colisaoInimigo se a bola de fogo não estiver na borda do mundo.
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
     * Método que define o que ocorre quando um objeto BolaDeFogo se encontra com um objeto Inimigo.
     * Se existir um inimigo Inimigo com quem entra em contato, causa dano ao inimigo e o remove se sua vida ficar menor ou igual a zero.
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
     * Método que retorna se a magia encostou no inimigo.
     */
    public boolean tocou()
    {
        return tocou;
    }
    
    /**
     * Altera o valor do booleano tocou para evitar que a magia cause dano adicional ao inimigo quando se encontrarem novamente.
     */
    public void alterarTocou()
    {
        tocou = false;
    }
    
    /**
     * Retorna o dano da magia.
     */
    public static int getDano()
    {
        return dano;
    }
    
    /**
     * Retorna a velocidade da magia.
     */
    public int getVelocidade()
    {
        return velocidade;
    }
    
    /**
     * Retorna o jogador associado à magia.
     */ 
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Aumenta o dano da magia de fogo.
     */
    public static void aumentarDano()
    {
        dano += 3;
    }
    
    /**
     * Método para reiniciar o dano da magia.
     */
    public void reiniciarBolaDeFogo()
    {
        dano = 10;
    }
}
