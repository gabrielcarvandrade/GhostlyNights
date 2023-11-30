import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * Classe responsável por cuidar da movimentação e interação do ataque do jogador aos seus inimigos por meio de objetos BolaDeFogo.
 * 
 * Autores: Gabriel Carvalho, Alexandre Carvalhaes, Douglas Silves, Ayron Sanfra. 
 * Versão: Marca 20.0
 */
public class BolaDeFogo extends Magia
{   
    // Imagem da bola de fogo
    private GreenfootImage imagem;
    
    // Tempo de espera entre os ataques
    private int cooldown;

    
    /**
     * Construtor da classe BolaDeFogo.
     * Define sua velocidade de movimento, quanto de dano causa ao inimigo e seu sprite.
     * Inicializa o cooldown para permitir ataques sucessivos.
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
     * Método que define o que ocorre quando um objeto BolaDeFogo se encontra com um objeto Inimigo.
     * Se existir um inimigo Inimigo com quem entra em contato, causa dano ao inimigo e o remove se sua vida ficar menor ou igual a zero.
     */
    protected void colisaoInimigo()
    {
        super.colisaoInimigo();
        
        // Se a bola de fogo atingir um inimigo, remove a bola de fogo do mundo
        if (tocou())
        {
            getWorld().removeObject(this);
            alterarTocou();
        }
    }
}
