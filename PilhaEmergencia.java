public class PilhaEmergencia {
    private Chamado[] elementos;
    private int topo;
    private int capacidade;

    public PilhaEmergencia(int capacidadeEscolhida) {
        // Garante o limite mínimo de 30 posições
        this.capacidade = Math.max(capacidadeEscolhida, 30);
        this.elementos = new Chamado[this.capacidade];
        this.topo = -1;
    }

    public void empilhar(Chamado c) {
        // Validação de overflow
        if (isCheia()) {
            System.out.println("Erro (Overflow): A Pilha de Emergência está cheia!");
            return;
        }
        topo++;
        elementos[topo] = c;
    }

    public Chamado desempilhar() {
        if (isVazia()) {
            return null;
        }
        Chamado c = elementos[topo];
        elementos[topo] = null; // Limpa a referência
        topo--;
        return c;
    }

    public boolean isCheia() { return topo == capacidade - 1; }
    public boolean isVazia() { return topo == -1; }
}