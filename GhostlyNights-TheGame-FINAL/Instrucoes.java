import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Instrucoes here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Instrucoes extends World
{
    /**
     * Constructor for objects of class Instrucoes.
     * 
     */
    public Instrucoes()
    {    
        super(800, 600, 1);
    }
    
    public void act()
    {
        voltarMenu();
    }
    
    private void voltarMenu(){
        //Verifica se a tecla "backspace" esta pressionada e seleciona o mundo da startScreen
        if (Greenfoot.isKeyDown("backspace")){
            Greenfoot.setWorld(new StartScreen());
        }
    }
}
