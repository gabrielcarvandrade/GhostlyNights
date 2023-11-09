import greenfoot.*;

/**
 * Classe com herança de World, responsável pela criação e posição de todos os objetos do jogo.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class MeuMundo extends World 
{
    private int count;
    private int cooldownSpawnFantasma;
    private int cooldownSpawnCoracao;
    private int randomizadorAltura;
    private int randomizadorLargura;
    private int randomizadorSpawn;
    private boolean modoAutomatico;
  
    private GreenfootSound music;
    private World world;
    private BolaDeFogo bolaDeFogo;
    private EsmeraldaDeXp iconeEsmeralda;
    private Player player;
    
    /**
     * Construtor da classe MeuMundo
     */
    public MeuMundo(boolean modoAutomatico) 
    {
        super(800, 600, 1);
        this.modoAutomatico = modoAutomatico;
        player = new Player(modoAutomatico);
        bolaDeFogo = new BolaDeFogo(player);
        iconeEsmeralda = new EsmeraldaDeXp();
        count = 0;
        cooldownSpawnFantasma = 60;
        cooldownSpawnCoracao = 660;
        prepare();
    }
    
    /**
     * Método responsável por adicionar os objetos ao mundo.
     */
    private void prepare() 
    {
        addObject(player, 400, 300);
        setPaintOrder(BarraExperiencia.class, Inimigo.class);
        // Cria a barra de HP
        BarraDeHP barraDeHP = player.getBarraDeHP();
        addObject(barraDeHP, player.getX(), player.getY() + player.getImage().getHeight() / 2 + 5);
        // Cria barra que mostra carregamento da Ult
        BarraUlt barraUlt = player.getBarraUlt();
        addObject(barraUlt, player.getX(), player.getY() + player.getImage().getHeight() + 5);
        //Cria barra de experiencia
        BarraExperiencia barraExperiencia = player.getBarraExperiencia();
        addObject(barraExperiencia, 400, 540);
        
        Som.tocarMusicaTema();
    }
    
    /**
     * Método de ação da classe MeuMundo.
     */
    public void act() 
    {
        count ++;
        if (player.isVivo()){
            spawnarFantasmas();
            spawnarCoracoes();
            Som.regularVolume();
        }
        else {
            mostrarPontuaçao();
        }
        restartGame();
    }
    
    /**
     * Método responsável pela geração dos inimigos(fantasmas).
     */
    private void spawnarFantasmas()
    {
        if (getObjects(Inimigo.class).size() == 0)
            addObject(new Inimigo(player), 0, 0);
            
        if (count % cooldownSpawnFantasma == 0) 
        {
            randomizadorLargura = Greenfoot.getRandomNumber(getWidth());
            randomizadorAltura = Greenfoot.getRandomNumber(getHeight());
            randomizadorSpawn = Greenfoot.getRandomNumber(4);
            switch(randomizadorSpawn) 
            {
                case 0 : addObject(new Inimigo(player), 0, randomizadorAltura); break;
                case 1 : addObject(new Inimigo(player), randomizadorLargura, 0); break;
                case 2 : addObject(new Inimigo(player), randomizadorLargura, 600); break;
                case 3 : addObject(new Inimigo(player), 800, randomizadorAltura); break;
            }
        }
    }
    
    /**
     * Método responsável pela geração dos corações.
     */
    private void spawnarCoracoes()
    {
       if (count % cooldownSpawnCoracao == 0) 
        {
            randomizadorLargura = Greenfoot.getRandomNumber(getWidth());
            randomizadorAltura = Greenfoot.getRandomNumber(getHeight());
            int ultimoCase = randomizadorSpawn;
            randomizadorSpawn = Greenfoot.getRandomNumber(30);
            if(randomizadorSpawn != ultimoCase)
                addObject(new Heart(), randomizadorLargura, randomizadorAltura);
        }
    }
    
    /**
     * Método responsável pela função de reiniciar o jogo.
     */
    private void restartGame()
    {
        if (!player.isVivo()) 
        {
            Som.pararMusicaTema();
            if (Greenfoot.isKeyDown("r"))
                Greenfoot.setWorld(new MeuMundo(modoAutomatico));
        }
    }
    
    /**
     * Método responsável por tocar a música tema do jogo.
     */
    public void started()
    {
        Som.tocarMusicaTema();   
    }
    
    /**
     * Método responsável por parar de tocar a música tema do jogo.
     */
    public void stopped()
    {
        Som.pararMusicaTema();
    }
    
    /**
     * Método de acesso da classe player.
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Método de aumento do atributo da velocidade de geração (Atualmente não usado, porém será util para proximas atualizaçoes)
     */
    public void aumentarVelocidadeSpawn()
    {
        Inimigo inimigo = new Inimigo(player);
        if(inimigo.aumentarSpawnFantasma() == true)
        {
            cooldownSpawnFantasma--;
        }
    }
    
    /**
     * Método responsável por exibir a mensagem pós fim de jogo, demonstrando vários atributos de pontuação do jogador.
     */
    private void mostrarPontuaçao()
    {
        Inimigo inimigo = new Inimigo(player); 
        showText("Você perdeu! " + "\n" + "- Voce sobreviveu por " + (player.getTempo()/60) + " segundos" + "\n" 
            +"- Voce matou "+ player.getInimigosMortos() + " inimigos" + "\n" + "- Nivel do Player: " + player.getNivelPlayer() + "\n"
            + "- Nivel Inimigo: "+ inimigo.getNivelInimigo() + "\n" + "Aperte R para recomeçar!", 400, 300);
    }
}
    
    

