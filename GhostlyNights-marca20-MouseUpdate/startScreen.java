import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe com herança de World que serve como tela inicial do jogo.
 * 
 */
public class startScreen extends World
{
    /**
     * Construtor da classe startScreen.
     * 
     */
    public startScreen()
    {    
        super(800, 600, 1);
    }
    
    /**
     * Classe que cuida do que deve acontecer enquanto o jogo estiver rodando na tela de menu
     */
    public void act()
    {
        startGame();
        regularVolume();
    }
    
    /**
     * Metodo que cuida de criar o World MeuMundo e parar a musica da tela de menu
     */
    public void startGame(){
        if (Greenfoot.isKeyDown("enter")){
            Greenfoot.setWorld(new MeuMundo());
            Som.pararMusicaMenu();
        }
    }
    
    /**
     * Metodo que chama outro metodo da classe Som para aumentar ou diminuir o som do jogo
     */
    public void regularVolume()
    {
        Som.regularVolume();
    }
    
    /**
     * Metodo que chama um metodo da classe Som para inicar a musica que ira tocar durante a tela de menu
     */
    public void started()
    {
        Som som = new Som();
        som.tocarMusicaMenu();   
    }
    
    /**
     * Metodo que chama outro metodo da classe Som para pausar o som
     */
    public void stopped ()
    {
        Som.pararMusicaMenu();
    }
}