import greenfoot.*;

/**
 * Classe que representa o inimigo do jogador, o fantasma, com suas interações e mudanças de atributos.
 * 
 * Autores: Gabriel Carvalho, Alexandre Carvalhaes, Douglas Silves, Ayron Sanfra. 
 * Versão: Marca 20.0
 */
public class Inimigo extends Actor 
{
    // Atributos do inimigo
    private int vida;
    private static int maxVida = 20;
    private static int dano = 20;
    private static int velocidade = 1;
    private static int nivelInimigo = 1;

    // Referência ao jogador
    private Player player;
    
    /**
     * Construtor da classe Inimigo.
     * @param player Referência ao jogador associado ao inimigo.
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
     * Método que define o comportamento do inimigo no jogo e suas ações.
     */
    public void act() 
    {
        movimentar();
        aumentarDificuldade();
    }
    
    /**
     * Método responsável pela movimentação do inimigo (fantasma).
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
     * Método que aumenta os atributos do fantasma depois de passado um certo tempo.
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
     * Método que retorna quanto de vida o inimigo possui para a classe BolaDeFogo.
     */
    public int obterVida()
    {
        return vida;
    }
    
    /**
     * Método que retorna a vida máxima que cada fantasma pode ter.
     */
    public int obterMaxVida()
    {
        return maxVida;
    }
    
    /**
     * Método de acesso ao dano do inimigo.
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
     * Método de acesso ao nível do inimigo.
     */
    public static int getNivelInimigo()
    {
        return nivelInimigo;
    }
    
    /**
     * Método para reiniciar os atributos do inimigo.
     */
    public void reiniciarInimigo()
    {
        nivelInimigo = 1;
        maxVida = 20;
        dano = 20;
    }
}
