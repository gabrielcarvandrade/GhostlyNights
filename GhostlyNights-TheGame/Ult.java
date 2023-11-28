import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ult here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ult extends Magia
{
    private static int dano = getDano()*3;
    private GreenfootImage imagem;
    
    /**
     * Construtor da classe BolaDeFogo
     * Define sua velocidade de movimento,
     * quanto de dano causa ao inimgo,
     * e seu sprite
     */
    public Ult(Player player, int velocidade, int volume)
    {
        super(player, velocidade, volume);
        imagem = new GreenfootImage("magiaUlt.png");
        imagem.scale(40, 24);
        setImage(imagem);
    }
    
    /**
     * Metodo que chama o metodo colisaoInimigo da classe pai BolaDeFogo
     */
    private void colisaoInimigo()
    {
        Inimigo inimigo = (Inimigo) getOneIntersectingObject(Inimigo.class);
        if (inimigo != null)
        {
            inimigo.alterarVida(dano);
            inimigo.move(-20);
            int coordenadaX = inimigo.getX();
            int coordenadaY = inimigo.getY();
            if (inimigo.obterVida() <= 0)
            {
                getWorld().addObject(new EsmeraldaDeXp(), coordenadaX, coordenadaY);
                getPlayer().addInimigosMortos();
                getWorld().removeObject(inimigo);
                
                Som.tocarDeathSound();
            }
        }
    }
}
