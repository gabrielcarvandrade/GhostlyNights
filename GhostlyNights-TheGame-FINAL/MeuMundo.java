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
    private String nomePlayer;
    
    private boolean modoAutomatico;
    private boolean dificuldadeMedia;
    private boolean dificuldadeAlta;
    private boolean dificuldadeMaxima;
  
    private World world;
    private Player player;
    private Placar placar;
    
    /**
     * Construtor da classe MeuMundo
     */
    public MeuMundo(boolean modoAutomatico, Placar placar) 
    {
        //Cria o mundo nas dimensoes 800x600
        super(800, 600, 1);
        
        //Inicializa as variaveis da classe
        cooldownSpawnFantasma = 60;
        cooldownSpawnCoracao = 660;
        contadorDificuldade = 0;
        
        //Salva numa variavel o nome do jogador baseado na entrada de informacao por uma caixa de pergunta
        nomePlayer = Greenfoot.ask("Digite um nome de no maximo 8 letras para seu player:");
        
        this.modoAutomatico = modoAutomatico;
        this.placar = placar;
        dificuldadeMedia = false;
        dificuldadeAlta = false;
        dificuldadeMaxima = false;
        
        //Cria e inicializa o jogador no modo de jogo atual
        player = new Player(modoAutomatico);
        
        //Adiciona os objetos ao mundo
        prepare();
    }
    
    /**
     * Método responsável por adicionar os objetos ao mundo.
     */
    private void prepare() 
    {
        //Adiciona o player ao mundo
        addObject(player, getHeight()/2, getWidth()/2);
        
        //Coloca o player e o inimigo acima de barradeexperiencia na questao de camadas de imagem
        setPaintOrder(Inimigo.class, BarraDeExperiencia.class);
        setPaintOrder(Player.class, BarraDeExperiencia.class);
        
        //Cria a barra de HP
        addObject(player.getBarraDeHP(), player.getX(), player.getY() + player.getImage().getHeight() / 2 + 5);
        
        //Cria barra que mostra carregamento da Ult
        addObject(player.getBarraUlt(), player.getX(), player.getY() + player.getImage().getHeight() + 5);
        
        //Cria barra de experiencia
        addObject(player.getBarraExperiencia(), 400, 540);
        
        //Toca a musica do jogo
        Som.tocarMusicaTema();
    }
    
    /**
     * Método de ação da classe MeuMundo.
     */
    public void act() 
    {
        //Caso o jogador esteja vivo, executa os metodos que fazem parte do jogo
        if (player.isVivo()){
            spawnarFantasmas();
            showText("Nivel do Inimigo: " + Inimigo.getNivelInimigo(), 120, 30);
            verificaDificuldade();
            spawnarCoracoes();
            Som.regularVolume();
            aumentarVelocidadeSpawn();
        }
        //Caso ele nao esteja vivo, mostra o placar do jogo
        else {
            long tempoSobrevivido = player.getTempo();
            player.removeJogador();
            mostrarPlacar(tempoSobrevivido);
        }
    }
    
    /**
     * Metodo para aumentar a dificuldade do jogo
     */
    private void verificaDificuldade()
    {
        //Verificacao do aumento de de dificuldade baseado em tempo jogado,
        //a cada verificacao aumenta 1 inimigo a mais a ser gerado na tela
        if(dificuldadeMedia)
        {
            spawnarFantasmas();
        } else if(dificuldadeAlta)
        {
            spawnarFantasmas();
            spawnarFantasmas();
        } else if(dificuldadeMaxima)
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
        //Utilizado para caso todos os inimigos na tela sejam mortos, gerar mais um, para a bola de fogo ter um alvo
        if (getObjects(Inimigo.class).size() == 0)
            addObject(new Inimigo(player), 0, 0);
        
        int randomizadorAltura;
        int randomizadorLargura;
        int randomizadorSpawn;
        
        //Randomiza a geraçao de inimigos
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
        //Randomiza a geracao de coracoes de cura
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
    public void mostrarPlacar(Long tempoSobrevivido)
    {
        //Envia as informacoes do nome do jogador, o tempo sobrevivido e o modo de jogo para o placar
        placar.setPlayerInfo(nomePlayer, tempoSobrevivido/60, modoAutomatico);
        //Utilizado para o placar mostrar apenas uma vez
        placar.mudarNaoMostrou();
        //Coloca o placar como mundo atual
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
        //Verifica com base no tempo de jogo, quando eh necessario aumentar a velocidade de geracao do inimigo e sua quantidade
        if(player.getTempo() % 500*Inimigo.getNivelInimigo() == 0)
        {
            cooldownSpawnFantasma-=3;
            
            contadorDificuldade += 5;
            //caso o contador de dificuldade atinja estas marcas, aciona os booleanos certos para aumentar a quantidade de inimigos gerados 
            if(contadorDificuldade == 20)
            {
                dificuldadeMedia = true;
            } else if(contadorDificuldade == 35)
            {
                dificuldadeMedia = false;
                dificuldadeAlta = true;
            } else if(contadorDificuldade == 50)
            {
                dificuldadeAlta = false;
                dificuldadeMaxima = true;
            }
        }
    }
}
    
    

