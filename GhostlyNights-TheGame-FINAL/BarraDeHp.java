import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe que representa a barra de pontos de vida (HP) do jogador.
 * 
 * Autor: (seu nome) 
 * Versão: (número da versão ou uma data)
 */
public class BarraDeHp extends Barra
{
    // Imagem para representar a barra de HP
    private GreenfootImage barra;

    /**
     * Construtor da classe BarraDeHP.
     * Inicializa as variáveis referentes à barra e define a posição inicial embaixo do jogador.
     */
    public BarraDeHp(Player player, int maxHP, int larguraOriginal) 
    {
        super(larguraOriginal, player);
        barra = new GreenfootImage(larguraOriginal, 10);
        atualizaImagem();
    }
    
    /**
     * Verifica a existência de um mundo e de um jogador para que seja possível mostrar a barra de HP.
     */
    public void act() 
    {
        if (getPlayer() != null && getWorld() != null) 
        {
            // Atualiza a posição da barra para corresponder à do jogador
            setLocation(getPlayer().getX(), getPlayer().getY() + getPlayer().getImage().getHeight() / 2 + 5);
        } 
        else 
        {
            // Se o jogador não estiver mais no mundo, remove a barra de HP
            getWorld().removeObject(this);
        }
        atualizaImagem();
    }

    /**
     * Método de atualização da imagem da barra de HP.
     * A barra de HP diminui apenas para o lado esquerdo.
     */
    public void atualizaImagem() 
    {
        barra.clear();
        int largura = getLarguraOriginal();
        
        if (getPlayer().getHP() > 0) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (getPlayer().getHP() * getLarguraOriginal()) / getPlayer().getMaxHp();
            barra.setColor(Color.GREEN);
            barra.fillRect(0, 0, novaLargura, 10);
            largura = novaLargura;
        }
        
        if (getPlayer().getHP() > 0 && getPlayer().getHP() <= getPlayer().getMaxHp()/10*2) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (getPlayer().getHP() * getLarguraOriginal()) / getPlayer().getMaxHp();
            barra.setColor(Color.RED);
            barra.fillRect(0, 0, novaLargura, 10);
        } 
        else if (getPlayer().getHP() > getPlayer().getMaxHp()/10*2 && getPlayer().getHP() <= getPlayer().getMaxHp()/10*6) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (getPlayer().getHP() * getLarguraOriginal()) / getPlayer().getMaxHp();
            barra.setColor(Color.YELLOW);
            barra.fillRect(0, 0, novaLargura, 10);
        }
        
        setImage(barra);
    }
}
