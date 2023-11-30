import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe que herda de World e serve como tela inicial do jogo.
 */
public class StartScreen extends World
{
    // Flag para controlar o modo de jogabilidade
    private boolean modoAutomatico;
    
    // Instância da classe Placar para passar como parâmetro para o próximo mundo
    private Placar placar;

    /**
     * Construtor da classe StartScreen.
     */
    public StartScreen()
    {    
        super(800, 600, 1);
        placar = new Placar();
        modoAutomatico = true;
    }

    /**
     * Método que controla as ações durante a execução do jogo na tela de menu.
     */
    public void act()
    {
        modoDeJogabilidade();
        startGame();
        regularVolume();
        instrucoes();
    }

    /**
     * Método que controla o modo de jogabilidade com as teclas "A" e "M".
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
     * Método que cria o mundo "MeuMundo" e inicia o jogo ao pressionar a tecla "enter".
     * Também interrompe a música da tela de menu.
     */
    private void startGame(){
        if (Greenfoot.isKeyDown("enter")){
            Greenfoot.setWorld(new MeuMundo(modoAutomatico, placar));
            Som.pararMusicaMenu();
        }
    }

    /**
     * Método que leva para as instruções ao pressionar a tecla "i".
     */
    private void instrucoes(){
        if (Greenfoot.isKeyDown("i")){
            Greenfoot.setWorld(new Instrucoes());
        }
    }

    /**
     * Método que ajusta o volume do som chamando um método da classe Som.
     */
    private void regularVolume()
    {
        Som.regularVolume();
    }

    /**
     * Método que inicia a música da tela de menu chamando um método da classe Som.
     */
    public void started()
    {
        Som som = new Som();
        som.tocarMusicaMenu();   
    }

    /**
     * Método que para a música chamando um método da classe Som.
     */
    public void stopped()
    {
        Som.pararMusicaMenu();
    }
}
