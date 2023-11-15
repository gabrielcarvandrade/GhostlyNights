import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe com herança de World que serve como tela inicial do jogo.
 * 
 */
public class StartScreen extends World
{
    private boolean modoAutomatico;
    private boolean modoManual;
    
    /**
     * Construtor da classe startScreen.
     * 
     */
    public StartScreen()
    {    
        super(800, 600, 1);
    }
    
    /**
     * Classe que cuida do que deve acontecer enquanto o jogo estiver rodando na tela de menu
     */
    public void act()
    {
        modoDeJogabilidade();
        startGame();
        regularVolume();
    }
    
    /**
     * Método que controla o modo de jogabilidade
     */
    private void modoDeJogabilidade()
    {
        if(Greenfoot.isKeyDown("A"))
        {
            modoAutomatico = true;    
        }
        else if(Greenfoot.isKeyDown("M"))
        {
            modoAutomatico = false;
        }
    }

    /**
     * Metodo que cuida de criar o World MeuMundo e parar a musica da tela de menu
     */
    public void startGame(){
        if (Greenfoot.isKeyDown("enter")){
            Greenfoot.setWorld(new MeuMundo(modoAutomatico));
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