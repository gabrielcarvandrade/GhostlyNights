 import greenfoot.*;

/**
 * Classe com herança de World, responsável pela criação e posição de todos os objetos do jogo.
 * 
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra. 
 * @version Marca 20.0
 */
public class MeuMundo extends World 
{
    private int cooldownSpawnFantasma;
    private int cooldownSpawnCoracao;
    private int contadorDificuldade;
    private long tempoInicial;
    private long tempoFinal;

    private String nomePlayer;
    
    private boolean modoAutomatico;
    private boolean dificuldadePequena;
    private boolean dificuldadeMedia;
    private boolean dificuldadeMax;
  
    private GreenfootSound music;
    private World world;
    private BolaDeFogo bolaDeFogo;
    private EsmeraldaDeXp iconeEsmeralda;
    private Player player;
    private Placar placar;
    
    /**
     * Construtor da classe MeuMundo
     */
    public MeuMundo(boolean modoAutomatico, Placar placar) 
    {
        super(800, 600, 1);
        nomePlayer = Greenfoot.ask("Digite um nome para seu player");
        this.modoAutomatico = modoAutomatico;
        this.placar = placar;
        dificuldadePequena = false;
        dificuldadeMedia = false;
        dificuldadeMax = false;
        player = new Player(modoAutomatico);
        bolaDeFogo = new BolaDeFogo(player, 3, 40);
        iconeEsmeralda = new EsmeraldaDeXp();
        cooldownSpawnFantasma = 60;
        cooldownSpawnCoracao = 660;
        contadorDificuldade = 0;
        tempoInicial = 0;
        tempoFinal = 0;
        prepare();
    }
    
    /**
     * Método responsável por adicionar os objetos ao mundo.
     */
    private void prepare() 
    {
        addObject(player, 400, 300);
        setPaintOrder(Inimigo.class, BarraDeExperiencia.class);
        setPaintOrder(Player.class, BarraDeExperiencia.class);
        // Cria a barra de HP
        addObject(player.getBarraDeHP(), player.getX(), player.getY() + player.getImage().getHeight() / 2 + 5);
        // Cria barra que mostra carregamento da Ult
        addObject(player.getBarraUlt(), player.getX(), player.getY() + player.getImage().getHeight() + 5);
        //Cria barra de experiencia
        addObject(player.getBarraExperiencia(), 400, 540);
        
        
        Som.tocarMusicaTema();
    }
    
    /**
     * Método de ação da classe MeuMundo.
     */
    public void act() 
    {
        if (player.isVivo()){
            spawnarFantasmas();
            showText("Nivel do Inimigo: " + Inimigo.getNivelInimigo(), 120, 30);
            verificaDificuldade();
            spawnarCoracoes();
            Som.regularVolume();
            aumentarVelocidadeSpawn();
            tempoFinal++;
        }
        else {
            mostrarPlacar();
        }
    }
    
    /**
     * Metodo para aumentar a dificuldade do jogo
     */
    private void verificaDificuldade()
    {
        if(dificuldadePequena)
            {
               spawnarFantasmas();
            } else if(dificuldadeMedia)
            {
               spawnarFantasmas();
               spawnarFantasmas();
            } else if(dificuldadeMax)
            {
               spawnarFantasmas();
               spawnarFantasmas();
               spawnarFantasmas();
            }
    }
    
    /**
     * Método responsável pela geração dos inimigos(fantasmas).
     */
    private void spawnarFantasmas()
    {
        if (getObjects(Inimigo.class).size() == 0)
            addObject(new Inimigo(player), 0, 0);
        
        int randomizadorAltura;
        int randomizadorLargura;
        int randomizadorSpawn;
            
        if (player.getTempo() % cooldownSpawnFantasma == 0) 
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
        int randomizadorAltura;
        int randomizadorLargura;
        int randomizadorSpawn;
        
        if (player.getTempo() % cooldownSpawnCoracao == 0) 
        {
            randomizadorLargura = Greenfoot.getRandomNumber(getWidth());
            randomizadorAltura = Greenfoot.getRandomNumber(getHeight());
            addObject(new Heart(), randomizadorLargura, randomizadorAltura);
        }
    }
    
    /**
     * Metodo responsavel pela geracao do placar.
     */
    public void mostrarPlacar()
    {
        long tempoSobrevivido = (tempoFinal - tempoInicial)/60;
        placar.setPlayerInfo(nomePlayer, tempoSobrevivido);
        placar.mudarNaoMostrou();
        Greenfoot.setWorld(placar);
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
     * Método de aumento do atributo da velocidade de geração 
     */
    private void aumentarVelocidadeSpawn()
    {
        if(player.getTempo() % 500*Inimigo.getNivelInimigo() == 0)
        {
            cooldownSpawnFantasma-=3;
            
            contadorDificuldade += 3;
            if(contadorDificuldade == 20)
            {
                dificuldadePequena = true;
            } else if(contadorDificuldade == 35)
            {
                dificuldadePequena = false;
                dificuldadeMedia = true;
            } else if(contadorDificuldade == 50)
            {
                dificuldadeMedia = false;
                dificuldadeMax = true;
            }
        }
    }
    
    /**
     * Metodo para passar os dados do player para a classe Placar
     */
    public void dadosPlayer()
    {
        
    }
}
    
    

