import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe referente a habilidade especial do jogador.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class UltBolaDeFogo extends Actor
{
    private int velocidade;
    private int dano;
    private int volume;
    private GreenfootImage imagem;
    private Player player;

    
    /**
     * Construtor da classe BolaDeFogo
     * Define sua velocidade de movimento,
     * quanto de dano causa ao inimgo,
     * e seu sprite
     */
    public UltBolaDeFogo(Player player)
    {
        velocidade = 3;
        BolaDeFogo magia = new BolaDeFogo(player);
        dano = magia.getDano()*3;
        volume = 30;
        this.player = player;
        imagem = new GreenfootImage("magiaUlt.png");
        imagem.scale(40, 24);
        setImage(imagem);
    }
    
    /**
     * Define a movimentaçao da bola de fogo no jogo
     * e chama o metodo colisaoInimigo se a bola de fogo nao estiver na borda do mundo
     */
    public void act()
    {
        move(velocidade*2);
        if (!isAtEdge())
            colisaoInimigo(player);
        else
            getWorld().removeObject(this);
    }
    
    /**
     * Metodo que define o que ocorre quando um objeto UltBolaDeFogo se encontra
     * com um objeto Inimigo
     * Se existir um inimigo Inimigo com quem entra em contato:
     * causa dano ao inimigo e remove ele se sua vida ficar menor ou igual 
     */
    private void colisaoInimigo(Player mago)
    {
        Fantasma fantasma = (Fantasma) getOneIntersectingObject(Fantasma.class);
        if (fantasma != null)
        {
            getWorld().removeObject(fantasma);
            mago.addInimigosMortos();
            Som.tocarDeathSound();
        }
    }
}

