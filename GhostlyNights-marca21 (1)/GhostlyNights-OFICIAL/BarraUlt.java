import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe que cuida da funcionalidade da barra que demonstra os pontos que possui ate atingir o maximo para lançar um ataque especial.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class BarraUlt extends Actor
{
    private int maxUlt;
    private int ult;
    private int larguraOriginal;
    
    private Player player; // Referência ao jogador
    private GreenfootImage barra;

    
    /**
     * Construtor da classe BarraUlt.
     * Inicializa as variáveis referentes à barra e define a posição inicial embaixo do jogador.
     */
    public BarraUlt(Player player) 
    {
        this.player = player; // Inicializa a referência ao jogador
        maxUlt = 20;
        ult = player.getPontosUlt();
        larguraOriginal = 40;
        barra = new GreenfootImage(larguraOriginal, 10);
        atualizaImagem();
    }
    
    /**
     * Verifica a existencia de um mundo e um player para que seja possivel aparecer a barra de Ult
     */
    public void act() 
    {
        if (player != null && player.getWorld() != null) 
            // Atualiza a posição da barra para corresponder à do jogador
            setLocation(player.getX(), player.getY() + player.getImage().getHeight() + 5);
        else
            // Se o jogador não estiver mais no mundo, remove a barra de HP
            getWorld().removeObject(this);
        ult = player.getPontosUlt();
        atualizaImagem();
    }
    
    /**
     * Método de atualização do tamanho da barra.
     * Funciona ajustando o tamanho da barra com base na energia restante.
     */
    public void lançaUlt() 
    {
        ult -= 20;
        if (ult < 0)
            ult = 0;
        atualizaImagem();
    }
    
    /**
     * Método de atualização da imagem da barra de Ult.
     * A barra de HP diminui apenas para o lado esquerdo.
     */
    private void atualizaImagem() 
    {
        barra.clear();
        
        if(ult == 20)
        {
            int novaLargura = (ult * larguraOriginal) / maxUlt;
            barra.setColor(Color.YELLOW);
            barra.fillRect(0, 0, novaLargura, 10);
        }
        
        if (ult > 0 && ult < 20) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (ult * larguraOriginal) / maxUlt;
            barra.setColor(Color.BLUE);
            barra.fillRect(0, 0, novaLargura, 10);
        }
        
        
        setImage(barra);
    }
    
    /**
     * Método de remoção da barra.
     * Verifica se o mundo existe e remove a barra.
     */
    public void removeBarraDeHP() 
    {
        if (getWorld() != null) 
            getWorld().removeObject(this);
    }
    
    public int getMaxUlt()
    {
        return maxUlt;
    }
}
