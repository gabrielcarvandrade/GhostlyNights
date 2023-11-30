import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Classe que representa a barra de experiência do jogador.
 * 
 * Autor: (seu nome) 
 * Versão: (número da versão ou uma data)
 */
public class BarraDeExperiencia extends Barra
{
    // Imagens para representar a barra de experiência e elementos visuais adicionais
    private GreenfootImage barra;
    private GreenfootImage esmeralda;
    private GreenfootImage bordaXp;
    
    /**
     * Construtor da classe BarraDeExperiencia.
     * Inicializa as variáveis referentes à barra e aos elementos visuais adicionais.
     */
    public BarraDeExperiencia(int larguraOriginal, Player player) 
    {
        super(larguraOriginal, player);
        bordaXp = new GreenfootImage("BordaXP.png");
        barra = new GreenfootImage(larguraOriginal, 12);
        
        atualizaImagem();
    }
    
    /**
     * Verifica a existência de um mundo e de um jogador para que seja possível mostrar a barra de experiência.
     */
    public void act() {
        // Desenha a borda da barra de experiência no mundo
        getWorld().getBackground().drawImage(bordaXp, 75, 530 + 1/5);
        
        if (getPlayer() == null && getWorld() == null) 
            // Se o jogador não estiver mais no mundo, remove a barra de experiência
            getWorld().removeObject(this);
        atualizaImagem();
    }

    /**
     * Método de atualização da imagem da barra de experiência.
     * A barra de experiência aumenta apenas para o lado direito.
     */
    private void atualizaImagem() {
        barra.clear();
        
        if (getPlayer().getExperiencia() > 0) {
            // Exibe o nível do jogador no canto superior direito da tela
            getWorld().showText("Nivel "+ getPlayer().getNivelPlayer(), 650, 530);
            
            // Calcula a nova largura da barra baseada na experiência acumulada
            int novaLargura = (getPlayer().getExperiencia() * getLarguraOriginal()) / getPlayer().getExperienciaNecessaria();
            barra.setColor(Color.GREEN);
            barra.fillRect(0, 0, novaLargura, 12);
        }
        
        setImage(barra);
    }
}
