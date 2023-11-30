import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe que representa a barra de habilidade "Ult" do jogador.
 * 
 * Autor: (seu nome) 
 * Versão: (número da versão ou uma data)
 */
public class BarraDaUlt extends Barra
{
    // Imagem para representar a barra de "Ult"
    private GreenfootImage barra;

    /**
     * Construtor da classe BarraDaUlt.
     * Inicializa as variáveis referentes à barra e define a posição inicial embaixo do jogador.
     */
    public BarraDaUlt(Player player, int larguraOriginal) 
    {
        super(larguraOriginal, player);
        barra = new GreenfootImage(larguraOriginal, 10);
        atualizaImagem();
    }
    
    /**
     * Verifica a existência de um mundo e de um jogador para que seja possível mostrar a barra de "Ult".
     */
    public void act() 
    {
        if (getPlayer() != null && getWorld() != null) 
            // Atualiza a posição da barra para corresponder à do jogador
            setLocation(getPlayer().getX(), getPlayer().getY() + getPlayer().getImage().getHeight() + 5);
        else
            // Se o jogador não estiver mais no mundo, remove a barra de "Ult"
            getWorld().removeObject(this);
        atualizaImagem();
    }
    
    /**
     * Método de atualização da imagem da barra de "Ult".
     * A barra de "Ult" pode diminuir apenas para o lado esquerdo.
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
            // Calcula a nova largura da barra baseada nos pontos de "Ult" restantes
            int novaLargura = (getPlayer().getPontosUlt() * getLarguraOriginal()) / getPlayer().getMaxUlt();
            barra.setColor(Color.BLUE);
            barra.fillRect(0, 0, novaLargura, 10);
        }
        
        setImage(barra);
    }
}
