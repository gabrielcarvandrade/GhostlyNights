import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe que representa uma barra no jogo.
 * 
 * Autor: (seu nome) 
 * Versão: (número da versão ou uma data)
 */
public class Barra extends Actor
{
    // Largura original da barra
    private int larguraOriginal;
    
    // Referência ao jogador associado à barra
    private Player player;
    
    /**
     * Construtor da classe Barra.
     * Inicializa a largura original e o jogador associado à barra.
     */
    public Barra(int larguraOriginal, Player player)
    {
        this.larguraOriginal = larguraOriginal;
        this.player = player;
    }
    
    /**
     * Método de acesso ao atributo player.
     * Retorna a instância do jogador associado à barra.
     */
    public Player getPlayer()
    {
        return player;
    }
    
    /**
     * Método de acesso ao atributo larguraOriginal.
     * Retorna a largura original da barra.
     */
    public int getLarguraOriginal()
    {
        return larguraOriginal;
    }
}
