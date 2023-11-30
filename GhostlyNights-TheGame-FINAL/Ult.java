    import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe que representa a habilidade "Ult" do jogador.
 * 
 * Autor: (seu nome) 
 * Versão: (número da versão ou uma data)
 */
public class Ult extends Magia
{
    // Dano causado pela habilidade "Ult"
    private static int dano = getDano() * 3;
    
    // Imagem para representar a habilidade
    private GreenfootImage imagem;
    
    /**
     * Construtor da classe Ult.
     * Define a velocidade de movimento, o dano causado ao inimigo e o sprite da habilidade.
     */
    public Ult(Player player, int velocidade, int volume)
    {
        super(player, velocidade, volume);
        imagem = new GreenfootImage("magiaUlt.png");
        imagem.scale(40, 24);
        setImage(imagem);
    }
    
    /**
     * Método que chama o método colisaoInimigo da classe pai Magia.
     */
    protected void colisaoInimigo()
    {
        super.colisaoInimigo();
    }
}
