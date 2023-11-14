import greenfoot.*;

/**
 * Classe que cuida da barra que domonstra quanto de experiencia o player acumulou para aumentar de nivel.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class ObjetosExternos extends Actor
{  
    private Player player;
    private GreenfootImage bordaXp;
    
    /**
     * Construtor da classe BarraDeHP.
     * Inicializa as variáveis referentes à barra e define a posição inicial embaixo do jogador.
     */
    public ObjetosExternos(Player player) 
    {
        this.player = player; // Inicializa a referência ao jogador
        bordaXp = new GreenfootImage("BordaXp.png");
    }
    
    /**
     * Verifica a existencia de um mundo e um player para que seja possivel aparecer a barra de HP
     */
    public void act() {
        if (player == null && getWorld() == null) 
            // Se o jogador não estiver mais no mundo, remove a barra de HP
            getWorld().removeObject(this);
    }
}
