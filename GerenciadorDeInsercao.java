public class GerenciadorDeInsercao {
    
    // Aplica a regra de inserção com base no nível de urgência
    public static void processarNovoChamado(Chamado novoChamado, PilhaEmergencia pilha, FilaComum fila) {
        
        // urgência >= 4 → pilha
        if (novoChamado.getNivelUrgencia() >= 4) {
            pilha.empilhar(novoChamado);
        } 
        // urgência < 4 → fila
        else {
            fila.enfileirar(novoChamado);
        }
    }
}