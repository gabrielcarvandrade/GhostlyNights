import greenfoot.*;

/**
 * Classe que cuida da funcionalidade da barra que domonstra o quanto de vida o player ainda possui no jogo.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class BarraDeHP extends Actor 
{
    private Player player; // Referência ao jogador
    private GreenfootImage barra;
    private int maxHP;
    private int hp;
    private int larguraOriginal;

    /**
     * Construtor da classe BarraDeHP.
     * Inicializa as variáveis referentes à barra e define a posição inicial embaixo do jogador.
     */
    public BarraDeHP(Player player, int maxHP) 
    {
        this.player = player; // Inicializa a referência ao jogador
        this.maxHP = maxHP;
        this.hp = maxHP;
        larguraOriginal = 40;
        barra = new GreenfootImage(larguraOriginal, 10);
        atualizaImagem();
    }
    
    /**
     * Verifica a existencia de um mundo e um player para que seja possivel aparecer a barra de HP
     */
    public void act() 
    {
        if (player != null && player.getWorld() != null) 
        {
            // Atualiza a posição da barra para corresponder à do jogador
            setLocation(player.getX(), player.getY() + player.getImage().getHeight() / 2 + 5);
        } else 
        {
            // Se o jogador não estiver mais no mundo, remove a barra de HP
            getWorld().removeObject(this);
        }
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
    private void atualizaImagem() 
    {
        barra.clear();
        int largura = larguraOriginal;
        if (hp > 0) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (hp * larguraOriginal) / maxHP;
            barra.setColor(Color.GREEN);
            barra.fillRect(0, 0, novaLargura, 10);
            largura = novaLargura;
        }
        
        if (hp > 0 && hp <=maxHP/10*2) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (hp * larguraOriginal) / maxHP;
            barra.setColor(Color.RED);
            barra.fillRect(0, 0, novaLargura, 10);
        } 
        else if (hp > maxHP/10*2 && hp <= maxHP/10*6) 
        {
            // Calcula a nova largura da barra baseada no HP restante
            int novaLargura = (hp * larguraOriginal) / maxHP;
            barra.setColor(Color.YELLOW);
            barra.fillRect(0, 0, novaLargura, 10);
        }
        
        setImage(barra);
    }

    /**
     * Método de remoção da barra.
     * Verifica se o mundo existe e remove a barra.
     */
    private void removeBarraDeHP() 
    {
        if (getWorld() != null) 
            getWorld().removeObject(this);
    }
}
