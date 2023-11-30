import greenfoot.*;

/**
 * Classe que representa a esmeralda, um objeto que concede experiência ao jogador.
 * 
 * Autores: Gabriel Carvalho, Alexandre Carvalhaes, Douglas Silves, Ayron Sanfra. 
 * Versão: Marca 20.0
 */
public class EsmeraldaDeXp extends Actor
{
    // Imagem da esmeralda
    private GreenfootImage imagem;
    
    /**
     * Construtor da classe EsmeraldaDeXP.
     * Inicializa a imagem da esmeralda e ajusta seu tamanho.
     */
    public EsmeraldaDeXp()
    {
        imagem = new GreenfootImage("Esmeralda.png");
        imagem.scale(20, 15);
        setImage(imagem);
    }
}
