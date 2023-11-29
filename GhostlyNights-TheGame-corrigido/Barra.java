import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Barra here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Barra extends Actor
{
    private int larguraOriginal;
    private Player player;
    
    /**
     * Construtor da classe pai Barra
     */
    public Barra(int larguraOriginal, Player player)
    {
        this.larguraOriginal = larguraOriginal;
        this.player = player;
    }
    
    /**
     * Metodo de acesso ao atributo player
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Metodo de acesso ao atributo player
     */
    public int getLarguraOriginal()
    {
        return larguraOriginal;
    }
}
