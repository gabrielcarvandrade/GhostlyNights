import greenfoot.*;

/**
 * Classe que cuida da barra que domonstra quanto de experiencia o player acumulou para aumentar de nivel.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class BarraExperiencia extends Actor
{  
    private int experienciaNecessaria;
    private int experiencia;
    private int larguraOriginal;
    
    private Player player;
    private GreenfootImage barra;
    private GreenfootImage esmeralda;
    
    /**
     * Construtor da classe BarraDeHP.
     * Inicializa as variáveis referentes à barra e define a posição inicial embaixo do jogador.
     */
    public BarraExperiencia(Player player) 
    {
        this.player = player; // Inicializa a referência ao jogador
        experienciaNecessaria = 15*player.getNivelPlayer();
        experiencia = player.getExperiencia();
        larguraOriginal = 600;
        barra = new GreenfootImage(larguraOriginal, 12);
        esmeralda = new GreenfootImage("Esmeralda.png");
        atualizaImagem();
    }
    
    /**
     * Verifica a existencia de um mundo e um player para que seja possivel aparecer a barra de HP
     */
    public void act() {
        if (player == null && getWorld() == null) 
            // Se o jogador não estiver mais no mundo, remove a barra de HP
            getWorld().removeObject(this);
        experienciaNecessaria = 15*player.getNivelPlayer();
        experiencia = player.getExperiencia();
        atualizaImagem();
    }

    /**
     * Método de atualização da imagem da barra de HP.
     * A barra de HP diminui apenas para o lado esquerdo.
     */
    private void atualizaImagem() {
        barra.clear();
        if (experiencia > 0) {
            // Calcula a nova largura da barra baseada na Xp que falta
            int novaLargura = (experiencia * larguraOriginal) / experienciaNecessaria;
            barra.setColor(Color.GREEN);
            barra.fillRect(0, 0, novaLargura, 12);
        }
        setImage(barra);
    }
    
    /**
     * Retorna a experiencia necessaria para aumentar de nivel
     */
    public int getExperienciaNecessaria()
    {
        return experienciaNecessaria;
    }
}
