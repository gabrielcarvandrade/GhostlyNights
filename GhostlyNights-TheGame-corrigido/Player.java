import greenfoot.*;
import java.util.ArrayList;

/**
 * Classe que funciona como player do jogador dentro do jogo.
 * @authors Gabriel Carvalho, Alexandre Carvalhaes, Douglas Slves, Ayron Sanfra.
 * @version Marca 20.0
 */
public class Player extends Actor 
{
    private int tempo;
    private int hp;
    private int maxHP;
    private int velocidade;
    private int tempoColisao;
    private int inimigosMortos;
    private int pontosUlt;
    private int maxUlt;
    private int nivelPlayer;
    private int experiencia;
    private static int experienciaNecessaria;
    
    private GreenfootImage[] image;
    private BarraDeHp barraDeHp;
    private BarraDaUlt barraUlt;
    private BarraDeExperiencia barraDeExperiencia;
    private Inimigo inimigoProximo;
    
    private boolean estaVivo;
    private boolean modoAutomatico;

    /**
     * Construtor da classe Player
     * inicializa todas as variáveis principais
     */
    public Player(boolean modoAutomatico) 
    {
        //Inicializa as variaveis da classe
        tempo = 0;
        hp = 100;
        maxHP = 100;
        velocidade = 3;
        tempoColisao = 0;
        inimigosMortos = 0;
        pontosUlt = 0;
        maxUlt = 20;
        nivelPlayer = 1;
        experienciaNecessaria = 15*nivelPlayer;

        //Inicializa a imagem do jogador e a coloca no mundo
        image = new GreenfootImage[2];
        image[0] = new GreenfootImage("Player.png");
        image[1] = new GreenfootImage("Player1.png");
        image[0].scale(27, 34);
        image[1].scale(27, 34);
        setImage(image[0]);
        

        // Cria a barra de HP
        barraDeHp = new BarraDeHp(this, maxHP, 40);
        // Cria barra que mostra carregamento da Ult
        barraUlt = new BarraDaUlt(this, 40);
        // Cria barra de experiencia
        barraDeExperiencia = new BarraDeExperiencia(600, this);
        
        estaVivo = true;
        this.modoAutomatico = modoAutomatico;
    }
    
    /**
     * Método de adição da barra ao mundo
     */
    public void adicionadoAoWorld(World world) 
    {
        //Caso o mundo nao seja nulo, adiciona a barraDeHp ao mundo
        if (world != null) 
            world.addObject(barraDeHp, getX(), getY() + getImage().getHeight() / 2 + 7);
    }

    /**
     * Método de ação do jogador
     * chama todos métodos de interação do jogador,
     * assim como seu movimento
     * Atualiza a posição da barra de HP com o jogador
     */
    public void act() 
    {
        //Caso o player esteja vivo, executa os metodos referentes a classe e incrementa o tempo
        if (estaVivo) 
        {
            tempo++;
            movimenta();
            heal();
            pegarEsmeralda();
            inimigoMaisProximo();
            
            //Caso o modo selecionado for Automatico, utiliza as classes de lancamento de magia automatica para lancar as magias
            if (modoAutomatico)
            {
                lançaMagiaAutomatico();
                lançaMagiaUltAutomatico();
            }
            //Caso o modo selecionado nao for Automatico, utiliza as classes de lancamento de magia manual 
            //para lancar as magias seguindo o mouse
            else
            {
                lançaMagiaManual();
                lançaMagiaUltManual();
            }
            //Aumenta os atributos do player
            aumentarAtributos();
            //Verifica sua colisao
            verificaColisao();
        }
    }

    /**
     * Método de movimentação do jogador
     * configura as 4 teclas(W,A,S,D) para seus respectivos movimentos baseados na velocidade do jogador
     */
    private void movimenta() 
    {
        //Possibilita movimentaçao pelas teclas "WASD" ou pelas setas
        if (Greenfoot.isKeyDown("W") || Greenfoot.isKeyDown("up"))
        {
            setLocation(getX(), getY() - velocidade);
            inimigoMaisProximo();
        }
        if (Greenfoot.isKeyDown("A") || Greenfoot.isKeyDown("left")) 
        {
            setImage(image[1]);
            setLocation(getX() - velocidade, getY());
            inimigoMaisProximo();
        }
        if (Greenfoot.isKeyDown("S") || Greenfoot.isKeyDown("down")) 
        {
            setLocation(getX(), getY() + velocidade);
            inimigoMaisProximo();
        }
        if (Greenfoot.isKeyDown("D") || Greenfoot.isKeyDown("right")) 
        {
            setImage(image[0]);
            setLocation(getX() + velocidade, getY());
            inimigoMaisProximo();
        }
    }

    /**
     * Método de verificação de colisão
     * atualiza a vida do jogador com base no numero de colisões com o inimigo
     * possui um delay entre as colisões, impedindo com que o jogador colida várias vezes em um pequeno espaço de tempo
     */
    private void verificaColisao() 
    {
        //Impede o player de receber dano quando em contato com um fantasma enquanto o cooldown for maior que 0
        //Verifica se inimigo entrou em contato com player e diminui a vida do inimigo caso tenham se encontrado
        //Caso a vida do inimigo seja igual ou menor a 0, remove o jogador do mundo
        if (tempoColisao > 0) 
            tempoColisao--;
        if (tempoColisao == 0) 
        {
            Inimigo inimigo = (Inimigo) getOneIntersectingObject(Inimigo.class);

            if (inimigo != null) 
            {
                hp -= inimigo.obterDanoInimigo();
                tempoColisao = 30;

                if (hp <= 0) 
                {
                    estaVivo = false;
                }
            }
        }
    }

    /**
     * Metodo de cura do player
     */
    private void heal() 
    {
        //Verifica se o player entrou em contato com o um objeto da classe Heart e se retornar true, aumenta a vida do player em 20
        //Se a vida do player ja estiver completa disconsidera o contato com esse objeto
        if(isTouching(Heart.class)) 
        {
            if(hp<=(maxHP-20)) 
            {
                removeTouching(Heart.class);
                Som.tocarPegarCoracao();
                hp+=20;
            }
        }
    }
    
    /**
     * Metodo que permite o player acumular experiencia dropada pelo fantasma quando morre
     */
    private void pegarEsmeralda()
    {
        //Atribui a esmeralda o objeto esmeralda que esta encontrando com o player
        //Se esmeralda for nulo nada acontece, mas se nao for o player recebe 3 de experiencia
        EsmeraldaDeXp esmeralda = (EsmeraldaDeXp) getOneIntersectingObject(EsmeraldaDeXp.class);
        if (esmeralda != null)
        {
            getWorld().removeObject(esmeralda);
            Som.tocarPegarEsmeralda();
            experiencia += 3;
        }
    }

    /**
     * Método de remoção do jogador e da barra de HP
     */
    public void removeJogador() 
    {
        World world = getWorld();
        if (world != null) 
        {
            world.removeObject(barraDeHp);
            world.removeObject(barraDeExperiencia);
            world.removeObject(barraUlt);
            world.removeObject(this);
        }
    }

    /**
     * Método de acesso do HP do player
     */
    public int getHP() 
    {
        return hp;
    }
    
    /**
     * Metodo de acesso ao hp maximo do player
     */
    public int getMaxHp()
    {
        return maxHP;
    }
    
    /**
     * Altera os atributos inimigosMortos e pontosUlt da classe player quando um inimigo morre
     */
    public void alterarAtributos()
    {
        addInimigosMortos();
        if(getPontosUlt() < maxUlt)
            addPontosUlt();
    }
    
    /**
     * Metodo de acesso do atributo tempo
     */
    public int getTempo() 
    {
        return tempo;
    }
    
    /**
     * Metodo de acesso ao nivel do player
     */
    public int getNivelPlayer()
    {
        return nivelPlayer;
    }
    
    /**
     * Método de acesso do booleano estaVivo
     */
    public boolean isVivo() 
    {
        return estaVivo;
    }
    
    /**
     * Método de acesso da barraDeHp
     */
    public BarraDeHp getBarraDeHP() 
    {
        return barraDeHp;
    }
    
    /**
     * Metodo de acesso da barraUlt
     */
    public BarraDaUlt getBarraUlt()
    {
        return barraUlt;
    }
    
    /**
     * Metodo de acesso da barra de experiencia
     */
    public BarraDeExperiencia getBarraExperiencia()
    {
        return barraDeExperiencia;
    }
    
    /**
     * Metodo para encontrar o inimigo mais proximo do player
     */
    private void inimigoMaisProximo()
    {
        if (getWorld().numberOfObjects() > 3) 
        {
            ArrayList<Inimigo> inimigos = new ArrayList<>(); // declara um ArrayList para a utilizaçao do metodo getNeighbours
            int raioDeDistancia = 100;
            int indice = 0;
            double distancia;
            double menorDistancia = 0;
            // Enquanto o ArrayList for nulo, o programa aumenta o raio de procura
            while (inimigos.size() == 0)
            {
                inimigos.addAll((ArrayList<Inimigo>) getObjectsInRange(raioDeDistancia, Inimigo.class)); // Adiciona ao collection inimigos todos os inimigos a uma distancia de 500 pixels
                raioDeDistancia += 100;
            }
            // Faz o calculo de qual o fantasma com a menor distancia do player
            for (int i = 0; i < inimigos.size(); i++)
            {
                distancia = Math.sqrt(Math.pow(getX()-inimigos.get(i).getX(), 2) + Math.pow(getY()-inimigos.get(i).getY(), 2));
                if (menorDistancia == 0)
                    menorDistancia = distancia;
                if (distancia < menorDistancia)
                    indice = i;
            }
            // Atribui o a inimigoProximo o inimigo com menor distancia do player
            if (inimigos != null)
                inimigoProximo = inimigos.get(indice);
        }
    }
    
    /*
     * Adiciona uma bola de fogo que vai em direçao ao inimigo mais proximo de tempo em tempo
     */
    private void lançaMagiaAutomatico()
    {
        World mundo = getWorld();
        BolaDeFogo bolaDeFogo = new BolaDeFogo(this, 4, 40);
        if(Inimigo.class!=null)
        {
            if (tempo % 30 == 0)
            {
                mundo.addObject(bolaDeFogo, getX(), getY());
                Som.tocarSomBolaDeFogo();
                if (inimigoProximo != null)
                    bolaDeFogo.turnTowards(inimigoProximo.getX(), inimigoProximo.getY()); // faz a bola ir em direçao ao primeiro inimigo detectado
            }
        }
    }

    /**
     * Adiciona uma bola de fogo que vai em direçao ao inimigo mais proximo de tempo em tempo
     */
    private void lançaMagiaUltAutomatico()
    {
        if(pontosUlt >= 20)
        {
            if(Inimigo.class!=null)
            {
                if (Greenfoot.isKeyDown("SPACE")) 
                {
                    World mundo = getWorld();
                    Ult ult = new Ult(this, 5, 40);
                    mundo.addObject(ult, getX(), getY());
                    Som.tocarSomBolaDeFogo();
                    if (inimigoProximo != null)
                    {
                        ult.turnTowards(inimigoProximo.getX(), inimigoProximo.getY()); // faz a bola ir em direçao ao primeiro inimigo detectado pelo metodo getNeighbours 
                    }
                    pontosUlt -= 20;
                    if (pontosUlt < 0)
                    {
                        pontosUlt = 0;
                    }
                }
            }
        }
    }

    /**
     * Adiciona uma bola de fogo que vai em direçao ao inimigo mais proximo de tempo em tempo
     */
    private void lançaMagiaManual()
    {
        World mundo = getWorld();
        BolaDeFogo bolaDeFogo = new BolaDeFogo(this, 4, 40);
        MouseInfo mouse = Greenfoot.getMouseInfo();
            if(mouse != null)
            {
                if (tempo % 30 == 0)
                {
                    turnTowards(mouse.getX(), mouse.getY());
                    Som.tocarSomBolaDeFogo();
                    bolaDeFogo.setRotation(getRotation());
                    setRotation(0);
                    mundo.addObject(bolaDeFogo, getX(), getY());
                }
        }
    }
    
    /**
     * Adiciona uma bola de fogo que vai em direçao ao inimigo mais proximo de tempo em tempo
     */
    private void lançaMagiaUltManual()
    {
        if(pontosUlt >= 20)
        {
            if (Greenfoot.isKeyDown("SPACE")) 
            {
                World mundo = getWorld();
                MouseInfo mouse = Greenfoot.getMouseInfo();
                Ult ult = new Ult(this, 5, 40);
                turnTowards(mouse.getX(), mouse.getY());
                Som.tocarSomBolaDeFogo();
                ult.setRotation(getRotation());
                setRotation(0);
                mundo.addObject(ult, getX(), getY());
                    
                if (pontosUlt < 0)
                    {
                        pontosUlt = 0;
                    }
            }
        }
    }
    
    /** 
    /**
     * Aumenta os atributos do jogador caso aumente de nivel
     */
    private void aumentarAtributos()
    {
        if (experiencia >= experienciaNecessaria)
        {
            nivelPlayer++;
            maxHP += 10;
            BolaDeFogo.aumentarDano();
            if(nivelPlayer % 5 == 0)velocidade++;
            experiencia = 0;
        }
    }
    
    /**
     * Metodo de acesso a variavel inimigosMortos
     */
    public int getInimigosMortos()
    {
        return inimigosMortos;        
    }
    
    /**
     * Adiciona mais inimigos mortos a contagem
     */
    public void addInimigosMortos()
    {
         inimigosMortos++;        
    }
    
    /**
     * Metodo de acesso a quantidade atual de pontos ult
     */
    public int getPontosUlt()
    {
        return pontosUlt;
    }

    /**
     * Método de acesso a quantidade de pontos da ult para lançar o especial
     */
    public int getMaxUlt()
    {
        return maxUlt;
    }
    
    /**
     * Adiciona pontos a contagem de pontos para lançar o especial
     */
    public void addPontosUlt()
    {
         pontosUlt ++;
    }
    
    /**
     * Metodo de acesso a variavel experiencia
     */
    public int getExperiencia()
    {
        return experiencia;
    }

    /**
     * Método de acesso a quantidade de pontos de experiencia necessarios para subir de nivel
     */
    public int getExperienciaNecessaria()
    {
        return experienciaNecessaria;
    }
}
