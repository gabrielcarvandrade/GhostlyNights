import greenfoot.*;

/**
 * Classe referente ao inimigo do player, o fantasma, possuindo suas interações e mudanças de atributos.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class Inimigo extends Actor 
{
    private int vida;
    private int dano;
    private int rotacao;
    private static int nivelInimigo;
    
    private Player player;
    
    boolean aumentaSpawn = false;
    
    /**
     * Construtor da classe Inimigo
     */
    public Inimigo(Player player) 
    {
        this.player = player;
        vida = 20;
        dano = 20;
        nivelInimigo = getNivelInimigo();
        setImage(new GreenfootImage("Fantasminha2.png"));
        getImage().scale(55, 55);
    }
    
    /**
     * Metodo que define o comportamento do inimigo no jogo
     * e suas ações.
     */
    public void act() 
    {
        //setRotation(rotacao);
        movimentar();
        //rotacao = getRotation();
        //setRotation(0);
        aumentarDificuldade();
    }
    
    /**
     * Método responsável pela movimentação do inimigo(fantasma).
     */
    private void movimentar()
    {
        if (player.isVivo())
        {
            move(1);
            if (!isTouching(Player.class))
                turnTowards(player.getX(), player.getY());
        }
    }
    
    /**
     * Metodo que aumenta os atributos do fantasma depois de passado um certo tempo.
     */
    public void aumentarDificuldade()
    {
        if (player.getTempo()/60 >= 15*nivelInimigo)
        {
            vida += 3*nivelInimigo;
            dano += 3*nivelInimigo;
            nivelInimigo++;
        }
        aumentaSpawn = true;
        aumentarSpawnFantasma();
        aumentaSpawn = false;
    }
    
    /**
     * Metodo que retorna quanto de vida o inimgo possui
     * para a classe BolaDeFogo.
     */
    public int obterVida()
    {
        return vida;
    }
    
    /**
     * Metodo de acesso ao dano do inimigo.
     */
    public int obterDanoInimigo(){
        return dano;
    }
    
    /**
     * Metodo de acesso ao nivel do inimigo.
     */
    public int getNivelInimigo()
    {
        return nivelInimigo;
    }
    
    /**
     * Altera o valor da vida do fantasma.
     */
    public void alterarVida(int dano)
    {
        vida -= dano;
    }
    
    /**
     * Aumenta o spawn de inimigos
     */
    public boolean aumentarSpawnFantasma()
    {
        return aumentaSpawn;
    }
}
