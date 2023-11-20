import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class BarraDeHpTeste here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BarraDeHp extends Barra
{
    private GreenfootImage barra;
    private int maxHP;
    private int hp;

    /**
     * Construtor da classe BarraDeHP.
     * Inicializa as variáveis referentes à barra e define a posição inicial embaixo do jogador.
     */
    public BarraDeHp(Player player, int maxHP, int larguraOriginal) 
    {
        super(larguraOriginal,player);
        this.maxHP = maxHP;
        hp = maxHP;
        barra = new GreenfootImage(larguraOriginal, 10);
        atualizaImagem();
    }
    
    /**
     * Verifica a existencia de um mundo e um player para que seja possivel aparecer a barra de HP
     */
    public void act() 
    {
        if (getPlayer() != null && getWorld() != null) 
        {
            // Atualiza a posição da barra para corresponder à do jogador
            setLocation(getPlayer().getX(), getPlayer().getY() + getPlayer().getImage().getHeight() / 2 + 5);
        } else 
        {
            // Se o jogador não estiver mais no mundo, remove a barra de HP
            getWorld().removeObject(this);
        }
        atualizaImagem();
    }
    
    /**
     * Método de atualização do tamanho da barra.
     * Funciona ajustando o tamanho da barra com base no HP restante.
     */
    public void ganhaHP(int heal) 
    {
        hp+=20;
        atualizaImagem();
    }

    /**
     * Método de atualização do tamanho da barra.
     * Funciona ajustando o tamanho da barra com base no HP restante.
     */
    public void perdeHP(int dano) 
    {
        if (dano > 0) 
        {
            hp -= dano;
            if (hp < 0) 
                hp = 0;
            atualizaImagem();
        }
    }

    /**
     * Método de atualização da imagem da barra de HP.
     * A barra de HP diminui apenas para o lado esquerdo.
     */
    public void atualizaImagem() 
    {
        barra.clear();
        int largura = getLarguraOriginal();
        if (hp > 0) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (hp * getLarguraOriginal()) / maxHP;
            barra.setColor(Color.GREEN);
            barra.fillRect(0, 0, novaLargura, 10);
            largura = novaLargura;
        }
        
        if (hp > 0 && hp <=maxHP/10*2) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (hp * getLarguraOriginal()) / maxHP;
            barra.setColor(Color.RED);
            barra.fillRect(0, 0, novaLargura, 10);
        } 
        else if (hp > maxHP/10*2 && hp <= maxHP/10*6) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (hp * getLarguraOriginal()) / maxHP;
            barra.setColor(Color.YELLOW);
            barra.fillRect(0, 0, novaLargura, 10);
        }
        
        setImage(barra);
    }
}
