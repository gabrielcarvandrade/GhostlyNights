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
    private static int maxVida = 20;
    private static int dano = 20;
    private static int velocidade = 1;
    private static int nivelInimigo = 1;

    private Player player;
    
    /**
     * Construtor da classe Inimigo
     */
    public Inimigo(Player player) 
    {
        this.player = player;
        vida = obterMaxVida();
        maxVida = obterMaxVida();
        dano = obterDanoInimigo();
        setImage(new GreenfootImage("Fantasminha2.png"));
        getImage().scale(55, 55);
    }
    
    /**
     * Metodo que define o comportamento do inimigo no jogo
     * e suas ações.
     */
    public void act() 
    {
        movimentar();
        aumentarDificuldade();
    }
    
    /**
     * Método responsável pela movimentação do inimigo(fantasma).
     */
    private void movimentar()
    {
        if (player.isVivo())
        {
            move(velocidade);
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
            dano+=4;
            maxVida+=4;
            nivelInimigo++;
            if(nivelInimigo % 10 == 0)
            {
                velocidade++;
            }
        }
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
     * Metodo que retorno a vida maxima que cada fantasma pode ter
     */
    private int obterMaxVida()
    {
        return maxVida;
    }
    
    /**
     * Metodo de acesso ao dano do inimigo.
     */
    public int obterDanoInimigo(){
        return dano;
    }
    
    /**
     * Altera o valor da vida do fantasma.
     */
    public void alterarVida(int dano)
    {
        vida -= dano;
    }
    
    /**
     * Metodo de acesso o nivel do inimgo
     */
    public static int getNivelInimigo()
    {
        return nivelInimigo;
    }
    
    /**
     * Metodo para reiniciar os atributos do inimigo
     */
    public void reiniciarInimigo()
    {
        nivelInimigo = 1;
        maxVida = 20;
        dano = 20;
    }
}
