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
    private int nivelPlayer;
    private int experiencia;
    
    private GreenfootImage[] image;
    private BarraDeHP barraDeHP;
    private BarraUlt barraUlt;
    private BarraExperiencia barraExperiencia;
    private Inimigo inimigoProximo;
    
    private boolean estaVivo;
    private boolean removido; // Booleano para evitar remoção repetida
    private boolean somFunciona;
    private boolean modoAutomatico;

    /**
     * Construtor da classe Player
     * inicializa todas as variáveis principais
     */
    public Player(boolean modoAutomatico) 
    {
        inimigosMortos = 0;
        experiencia = inimigosMortos;
        pontosUlt = 0;
        tempo = 0;
        nivelPlayer = 1;
        hp = 100;
        maxHP = 100;
        velocidade = 3;
        estaVivo = true;
        tempoColisao = 0;
        image = new GreenfootImage[2];
        image[0] = new GreenfootImage("Player.png");
        image[1] = new GreenfootImage("Player1.png");
        image[0].scale(27, 34);
        image[1].scale(27, 34);
        
        
        setImage(image[0]);
        
        this.modoAutomatico = modoAutomatico;
        // Cria a barra de HP
        barraDeHP = new BarraDeHP(this, hp);
        // Cria barra que mostra carregamento da Ult
        barraUlt = new BarraUlt(this);
        // Cria barra de experiencia
        barraExperiencia = new BarraExperiencia(this);
        
        removido = false;
    }
    
    /**
     * Método de adição da barra ao mundo
     */
    public void adicionadoAoWorld(World world) 
    {
        if (world != null) 
            world.addObject(barraDeHP, getX(), getY() + getImage().getHeight() / 2 + 7);
    }

    /**
     * Método de ação do jogador
     * chama todos métodos de interação do jogador,
     * assim como seu movimento
     * Atualiza a posição da barra de HP com o jogador
     */
    public void act() 
    {
        if (estaVivo) 
        {
            tempo++;
            movimenta();
            heal();
            pegarEsmeralda();
            inimigoMaisProximo();
            
            if (modoAutomatico)
            {
                lançaMagiaAutomatico();
                lançaMagiaUltAutomatico();
            }
            else
            {
                lançaMagiaManual();
                lançaMagiaUltManual();
            }
            aumentarAtributos();
            verificaColisao();
        }
    }

    /**
     * Método de movimentação do jogador
     * configura as 4 teclas(W,A,S,D) para seus respectivos movimentos baseados na velocidade do jogador
     */
    private void movimenta() 
    {
        // Possibilita movimentaçao pelas teclas "WASD"
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
        if (tempoColisao > 0) 
            tempoColisao--;
        if (tempoColisao == 0) 
        {
            Inimigo inimigo = (Inimigo) getOneIntersectingObject(Inimigo.class);

            if (inimigo != null) 
            {
                hp -= inimigo.obterDanoInimigo();
                barraDeHP.perdeHP(20); // Atualiza a barra de HP
                tempoColisao = 30;

                if (hp <= 0) 
                {
                    estaVivo = false;

                    if (!removido) 
                    {
                        removeJogador();
                        removido = true;
                    }
                }
            }
        }
    }

    /**
     * Metodo de cura do player
     */
    private void heal() 
    {
        if(isTouching(Heart.class)) 
        {
            if(hp<=(maxHP-20)) 
            {
                removeTouching(Heart.class);
                Som.tocarPegarCoracao();
                hp+=20;
                barraDeHP.ganhaHP(20);
            }
        }
    }
    
    /**
     * Metodo que permite o player acumular experiencia dropada pelo fantasma quando morre
     */
    private void pegarEsmeralda()
    {
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
    private void removeJogador() 
    {
        World world = getWorld();
        if (world != null) 
        {
            world.removeObject(barraDeHP);
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
     * Altera os atributos inimigosMortos e pontosUlt da classe player quando um inimigo morre
     */
    public void alterarAtributos()
    {
        addInimigosMortos();
        if(getPontosUlt() < barraUlt.getMaxUlt())
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
    public BarraDeHP getBarraDeHP() 
    {
        return barraDeHP;
    }
    
    /**
     * Metodo de acesso da barraUlt
     */
    public BarraUlt getBarraUlt()
    {
        return barraUlt;
    }
    
    /**
     * Metodo de acesso da barra de experiencia
     */
    public BarraExperiencia getBarraExperiencia()
    {
        return barraExperiencia;
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
        BolaDeFogo bolaDeFogo = new BolaDeFogo(this);
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
                    UltBolaDeFogo ultBolaDeFogo = new UltBolaDeFogo(this);
                    mundo.addObject(ultBolaDeFogo, getX(), getY());
                    Som.tocarSomBolaDeFogo();
                    if (inimigoProximo != null)
                    {
                        ultBolaDeFogo.turnTowards(inimigoProximo.getX(), inimigoProximo.getY()); // faz a bola ir em direçao ao primeiro inimigo detectado pelo metodo getNeighbours 
                    }
                    pontosUlt -= 20;
                }
            }
            barraUlt.lançaUlt();
        }
    }

    /**
     * Adiciona uma bola de fogo que vai em direçao ao inimigo mais proximo de tempo em tempo
     */
    private void lançaMagiaManual()
    {
        World mundo = getWorld();
        BolaDeFogo bolaDeFogo = new BolaDeFogo(this);
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
                UltBolaDeFogo ultBolaDeFogo = new UltBolaDeFogo(this);
                turnTowards(mouse.getX(), mouse.getY());
                Som.tocarSomBolaDeFogo();
                ultBolaDeFogo.setRotation(getRotation());
                setRotation(0);
                mundo.addObject(ultBolaDeFogo, getX(), getY());
                    
                pontosUlt -= 20;
            }
            barraUlt.lançaUlt();
        }
    }
    
    /** 
    /**
     * Aumenta os atributos do jogador caso aumente de nivel
     */
    private void aumentarAtributos()
    {
        if (experiencia >= barraExperiencia.getExperienciaNecessaria())
        {
            nivelPlayer++;
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
     * Metodo de acesso a quantidade de pontos para lançar o especial
     */
    public int getPontosUlt()
    {
        return pontosUlt;
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
}
