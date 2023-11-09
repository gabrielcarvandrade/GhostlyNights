import greenfoot.*;

/**
 * Classe referente ao fantasma, possuindo suas interações e mudanças de atributos.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class Fantasma extends Actor 
{
    private int vida;
    private int dano;
    private int rotacao;
    private static int nivelFantasma;
    
    private Player player;
    
    boolean aumentaSpawn = false;
    
    /**
     * Construtor da classe Fantasma
     */
    public Fantasma(Player player) 
    {
        this.player = player;
        vida = 20;
        dano = 20;
        nivelFantasma = getNivelFantasma();
        setImage(new GreenfootImage("Fantasminha2.png"));
        getImage().scale(55, 55);
    }
    
    /**
     * Metodo que define o comportamento do fantasma no jogo
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
     * Método responsável pela movimentação do fantasma.
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
        if (player.getTempo()/60 >= 15*nivelFantasma)
        {
            vida += 3*nivelFantasma;
            dano += 3*nivelFantasma;
            nivelFantasma++;
        }
        aumentaSpawn = true;
        aumentarSpawnFantasma();
        aumentaSpawn = false;
    }
    
    /**
     * Metodo que retorna quanto de vida o fantasma possui
     * para a classe BolaDeFogo.
     */
    public int obterVida()
    {
        return vida;
    }
    
    /**
     * Metodo de acesso ao dano do fantasma.
     */
    public int obterDanoFantasma(){
        return dano;
    }
    
    /**
     * Metodo de acesso ao nivel do fantasma.
     */
    public int getNivelFantasma()
    {
        return nivelFantasma;
    }
    
    /**
     * Altera o valor da vida do fantasma.
     */
    public void alterarVida(int dano)
    {
        vida -= dano;
    }
    
    /**
     * Aumenta o spawn de fantasma
     */
    public boolean aumentarSpawnFantasma()
    {
        return aumentaSpawn;
    }
}
