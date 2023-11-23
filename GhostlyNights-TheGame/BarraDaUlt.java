import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BarraDaUlt here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BarraDaUlt extends Barra
{
    private GreenfootImage barra;

    
    /**
     * Construtor da classe BarraUlt.
     * Inicializa as variáveis referentes à barra e define a posição inicial embaixo do jogador.
     */
    public BarraDaUlt(Player player, int larguraOriginal) 
    {
        super(larguraOriginal, player);
        barra = new GreenfootImage(larguraOriginal, 10);
        atualizaImagem();
    }
    
    /**
     * Verifica a existencia de um mundo e um player para que seja possivel aparecer a barra de Ult
     */
    public void act() 
    {
        if (getPlayer() != null && getWorld() != null) 
            // Atualiza a posição da barra para corresponder à do jogador
            setLocation(getPlayer().getX(), getPlayer().getY() + getPlayer().getImage().getHeight() + 5);
        else
            // Se o jogador não estiver mais no mundo, remove a barra de HP
            getWorld().removeObject(this);
        atualizaImagem();
    }
    
    /**
     * Método de atualização da imagem da barra de Ult.
     * A barra de HP diminui apenas para o lado esquerdo.
     */
    private void atualizaImagem() 
    {
        barra.clear();
        
        if(getPlayer().getPontosUlt() == 20)
        {
            int novaLargura = (getPlayer().getPontosUlt() * getLarguraOriginal()) / getPlayer().getMaxUlt();
            barra.setColor(Color.YELLOW);
            barra.fillRect(0, 0, novaLargura, 10);
        }
        
        if (getPlayer().getPontosUlt() > 0 && getPlayer().getPontosUlt() < 20) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (getPlayer().getPontosUlt() * getLarguraOriginal()) / getPlayer().getMaxUlt();
            barra.setColor(Color.BLUE);
            barra.fillRect(0, 0, novaLargura, 10);
        }
        
        
        setImage(barra);
    }
}
