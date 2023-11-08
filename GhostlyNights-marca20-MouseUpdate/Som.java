import greenfoot.*;

/**
 * Classe referente ao tratamento de todos os sons do jogo.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class Som  
{
    private static int volume;
    private static boolean testeSom;
    
    private static GreenfootSound musicaTema;
    private static GreenfootSound menuMusica;
    private static GreenfootSound deathSound;
    private static GreenfootSound pegarEsmeraldaSom;
    private static GreenfootSound bolaDeFogoSom;
    private static GreenfootSound pegarCoracaoSom;

    /** 
     * Constructor for objects of class Som
     */
    public Som()
    {
        volume = 30;
        
        musicaTema = new GreenfootSound("music.mp3");
        menuMusica = new GreenfootSound("menuMusic.mp3");
       //deathSound = new GreenfootSound ("enemyDeath.mp3");
       //pegarEsmeraldaSom = new GreenfootSound("pegarEsmeraldaSom3.mp3");
        //bolaDeFogoSom = new GreenfootSound ("bolaDeFogo.mp3");
        //pegarCoracaoSom = new GreenfootSound ("heartPickupSound.mp3");
    }
    
    /**
     * Metodo que retorna o booleano que verifica se a musica 
     */
    public static boolean getTesteSom()
    {
        return testeSom;
    }

    /**
     * Metodo para tocar a musica do jogo
     */
    public static void tocarMusicaTema()
    {
        try 
        {
            musicaTema.playLoop();
            musicaTema.setVolume(volume);
            
            testeSom = true;
        }
        catch (Exception e) 
        {
            System.out.println("Ops.. não foi possível tocar os sons do jogo");
            
            testeSom = false;
        }
    }
    
    /**
     * Metodo para parar de tocar a musica tema do jogo
     */
    public static void pararMusicaTema()
    {
        musicaTema.pause();
    }
    
    /**
     * Metodo para tocar a musica do menu do jogo
     */
    public void tocarMusicaMenu()
    {
        menuMusica = new GreenfootSound("menuMusic.mp3");
        try 
        {
            menuMusica.playLoop();
            menuMusica.setVolume(volume);
            testeSom = true;
        }
        catch (Exception e) 
        {
            System.out.println("Ops.. não foi possível tocar os sons do jogo");
            testeSom = false;
        }
    }
    
    /**
     * Metodo para parar de tocar a musica tema do jogo
     */
    public static void pararMusicaMenu()
    {
        menuMusica.stop();
        menuMusica.setVolume(volume);
    }
    
    /**
     * Metodo para tocar o som da morte do inimigo
     */
    public static void tocarDeathSound()
    {
        deathSound = new GreenfootSound ("enemyDeath.mp3");
        deathSound.play();
        deathSound.setVolume(40);
    }
    
    /**
     * Metodo para tocar o som de pegar o coracao
     */
    public static void tocarPegarCoracao()
    {
        pegarCoracaoSom = new GreenfootSound ("heartPickupSound.mp3");
        pegarCoracaoSom.play();
        pegarCoracaoSom.setVolume(50);
    }
    
    /**
     * Metodo para tocar o som de pegar a esmeralda de experiencia
     */
    public static void tocarPegarEsmeralda()
    {
        pegarEsmeraldaSom = new GreenfootSound("pegarEsmeraldaSomNovo.mp3");
        pegarEsmeraldaSom.play();
        pegarEsmeraldaSom.setVolume(50);
    }
    
    /**
     * Metodo para tocar o som da bola de fogo
     */
    public static void tocarSomBolaDeFogo()
    {
        bolaDeFogoSom = new GreenfootSound ("bolaDeFogo.mp3");
        bolaDeFogoSom.play();
        bolaDeFogoSom.setVolume(40);
    }
    
    /**
     * Metodo de possibilita aumentar ou diminuir o volume das musicas de menu e tema do jogo
     */
    public static void regularVolume()
    {
        if ( Greenfoot.isKeyDown ("-") ) 
            volume--;
        if ( Greenfoot.isKeyDown ("+") ) 
            volume++;
        if(testeSom)
        {
            musicaTema.setVolume(volume);
            menuMusica.setVolume(volume);
        }
    }
}
