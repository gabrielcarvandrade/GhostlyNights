import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BarraDeExperiencia here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BarraDeExperiencia extends Barra
{
    private GreenfootImage barra;
    private GreenfootImage esmeralda;
    private GreenfootImage bordaXp;
    
    /**
     * Construtor da classe BarraDeExperiencia.
     */
    public BarraDeExperiencia(int larguraOriginal, Player player) 
    {
        super(larguraOriginal, player);
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
        atualizaImagem();
    }

    /**
     * Método de atualização da imagem da barra de HP.
     * A barra de HP diminui apenas para o lado esquerdo.
     */
    private void atualizaImagem() {
        barra.clear();
        if (getPlayer().getExperiencia() > 0) {
            getWorld().showText("Nivel "+ getPlayer().getNivelPlayer(), 650, 530);
            
            // Calcula a nova largura da barra baseada na Xp que falta
            int novaLargura = (getPlayer().getExperiencia() * getLarguraOriginal()) / getPlayer().getExperienciaNecessaria();
            barra.setColor(Color.GREEN);
            barra.fillRect(0, 0, novaLargura, 12);
        }
        setImage(barra);
    }
}
