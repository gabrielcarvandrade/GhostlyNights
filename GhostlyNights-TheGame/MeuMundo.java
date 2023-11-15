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
    private int contadorDificuldade;
    private static int nivelInimigo;
    
    private boolean modoAutomatico;
    private boolean dificuldadePequena;
    private boolean dificuldadeMedia;
    private boolean dificuldadeMax;
  
    private GreenfootSound music;
    private World world;
    private BolaDeFogo bolaDeFogo;
    private EsmeraldaDeXp iconeEsmeralda;
    private GreenfootImage bordaXp;
    private Player player;
    private ObjetosExternos objetosExternos;
    
    /**
     * Construtor da classe MeuMundo
     */
    public MeuMundo(boolean modoAutomatico) 
    {
        super(800, 600, 1);
        this.modoAutomatico = modoAutomatico;
        dificuldadePequena = false;
        dificuldadeMedia = false;
        dificuldadeMax = false;
        nivelInimigo = 1;
        player = new Player(modoAutomatico);
        bolaDeFogo = new BolaDeFogo(player);
        iconeEsmeralda = new EsmeraldaDeXp();
        bordaXp = new GreenfootImage("BordaXP.png");
        count = 0;
        cooldownSpawnFantasma = 60;
        cooldownSpawnCoracao = 660;
        contadorDificuldade = 0;
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
        getBackground().drawImage(bordaXp, 75, 530+1/5);
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
            spawnarCoracoes();
            Som.regularVolume();
            
            aumentarVelocidadeSpawn();
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
     * Método de aumento do atributo da velocidade de geração 
     */
    public void aumentarVelocidadeSpawn()
    {
        if(count % 900*Inimigo.getNivelInimigo() == 0)
        {
            cooldownSpawnFantasma-=3;
            
            contadorDificuldade++;
            if(contadorDificuldade == 20)
            {
                dificuldadePequena = true;
            } else if(contadorDificuldade == 35)
            {
                dificuldadeMedia = true;
            } else if(contadorDificuldade == 50)
            {
                dificuldadeMax = true;
            }
        }
    }
    
    /**
     * Método responsável por exibir a mensagem pós fim de jogo, demonstrando vários atributos de pontuação do jogador.
     */
    private void mostrarPontuaçao()
    {
        int largura = 350;
        int altura = 350;
        
        int x = (getWidth() - largura) / 2;
        int y = (getHeight() - altura) / 2;
        
        GreenfootImage fundoPontuacao = new GreenfootImage(largura, altura);
        fundoPontuacao.setColor(Color.WHITE);
        fundoPontuacao.fillRect(0, 0, largura, altura);
        
        GreenfootImage textoPerdeu = getBackground();
        textoPerdeu.drawImage(fundoPontuacao, x, y);
        Font fontePerdeu = new Font(true, true, 50);
        textoPerdeu.setFont(fontePerdeu);
        textoPerdeu.setColor(Color.RED);
        textoPerdeu.drawString("Você perdeu! ", 240, 200);
        
        showText("- Voce sobreviveu por " + (player.getTempo()/60) + " segundos" + "\n" 
            +"- Voce matou "+ player.getInimigosMortos() + " inimigos" + "\n" + "- Nivel do Player: " + player.getNivelPlayer() + "\n"
            + "- Nivel Inimigo: "+ Inimigo.getNivelInimigo() + "\n" + "Aperte R para recomeçar!", 400, 300);
    }
}
    
    

