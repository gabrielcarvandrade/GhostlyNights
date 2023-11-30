import greenfoot.*;

/**
 * Classe responsável pelo tratamento de todos os sons do jogo.
 * 
 * Autores: Gabriel Carvalho, Alexandre Carvalhaes, Douglas Silves, Ayron Sanfra. 
 * Versão: Marca 20.0
 */
public class Som  
{
    // Volume dos sons
    private static int volume;
    
    // Variável de controle para testar se o som está funcionando
    private static boolean testeSom;
    
    // Sons do jogo
    private static GreenfootSound musicaTema;
    private static GreenfootSound menuMusica;
    private static GreenfootSound deathSound;
    private static GreenfootSound pegarEsmeraldaSom;
    private static GreenfootSound bolaDeFogoSom;
    private static GreenfootSound pegarCoracaoSom;

    /** 
     * Construtor da classe Som.
     * Inicializa o volume e os sons do jogo.
     */
    public Som()
    {
        volume = 30;
        
        musicaTema = new GreenfootSound("music.mp3");
        menuMusica = new GreenfootSound("menuMusic.mp3");
    }
    
    /**
     * Método que retorna o booleano que verifica se a música está tocando.
     */
    public static boolean getTesteSom()
    {
        return testeSom;
    }

    /**
     * Método para tocar a música tema do jogo.
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
     * Método para parar de tocar a música tema do jogo.
     */
    public static void pararMusicaTema()
    {
        musicaTema.pause();
    }
    
    /**
     * Método para tocar a música do menu do jogo.
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
     * Método para parar de tocar a música do menu do jogo.
     */
    public static void pararMusicaMenu()
    {
        menuMusica.stop();
        if (testeSom)
            menuMusica.setVolume(volume);
    }
    
    /**
     * Método para tocar o som da morte do inimigo.
     */
    public static void tocarDeathSound()
    {
        if (testeSom)
        {
            deathSound = new GreenfootSound("enemyDeath.mp3");
            deathSound.play();
            deathSound.setVolume(40);            
        }
    }
    
    /**
     * Método para tocar o som de pegar o coração.
     */
    public static void tocarPegarCoracao()
    {
        if (testeSom)
        {
            pegarCoracaoSom = new GreenfootSound("heartPickupSound.mp3");
            pegarCoracaoSom.play();
            pegarCoracaoSom.setVolume(50);      
        }
    }
    
    /**
     * Método para tocar o som de pegar a esmeralda de experiência.
     */
    public static void tocarPegarEsmeralda()
    {
        if (testeSom)
        {
            pegarEsmeraldaSom = new GreenfootSound("pegarEsmeraldaSomNovo.mp3");
            pegarEsmeraldaSom.play();
            pegarEsmeraldaSom.setVolume(50);
        }
    }
    
    /**
     * Método para tocar o som da bola de fogo.
     */
    public static void tocarSomBolaDeFogo()
    {
        if (testeSom)
        {
            bolaDeFogoSom = new GreenfootSound("bolaDeFogo.mp3");
            bolaDeFogoSom.play();
            bolaDeFogoSom.setVolume(40);
        }
    }
    
    /**
     * Método que possibilita aumentar ou diminuir o volume das músicas de menu e tema do jogo.
     */
    public static void regularVolume()
    {
        if (Greenfoot.isKeyDown("-")) 
            volume--;
        if (Greenfoot.isKeyDown("+")) 
            volume++;
        if (testeSom)
        {
            musicaTema.setVolume(volume);
            menuMusica.setVolume(volume);
        }
    }
}
