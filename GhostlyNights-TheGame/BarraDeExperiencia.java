import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BarraDeExperiencia here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BarraDeExperiencia extends Barra
{
    private int experienciaNecessaria;
    private int experiencia;
    
    private GreenfootImage barra;
    private GreenfootImage esmeralda;
    private GreenfootImage bordaXp;
    
    /**
     * Construtor da classe BarraDeExperiencia.
     */
    public BarraDeExperiencia(int larguraOriginal, Player player) 
    {
        super(larguraOriginal, player);
        experienciaNecessaria = 15*player.getNivelPlayer();
        experiencia = player.getExperiencia();
        bordaXp = new GreenfootImage("BordaXP.png");
        barra = new GreenfootImage(larguraOriginal, 12);
        atualizaImagem();
    }
    
    /**
     * Verifica a existencia de um mundo e um player para que seja possivel aparecer a barra de experiencia
     */
    public void act() {
        getWorld().getBackground().drawImage(bordaXp, 75, 530+1/5);
        if (getPlayer() == null && getWorld() == null) 
            // Se o jogador não estiver mais no mundo, remove a barra de HP
            getWorld().removeObject(this);
        experienciaNecessaria = 15*getPlayer().getNivelPlayer();
        experiencia = getPlayer().getExperiencia();
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
            int novaLargura = (experiencia * getLarguraOriginal()) / experienciaNecessaria;
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
