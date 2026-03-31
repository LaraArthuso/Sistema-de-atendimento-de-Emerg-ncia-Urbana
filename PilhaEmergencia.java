public class PilhaEmergencia {
    private Chamado[] elementos;
    private int topo;
    private int capacidade;

    public PilhaEmergencia(int capacidadeEscolhida) {
        this.capacidade = Math.max(capacidadeEscolhida, 30);
        this.elementos = new Chamado[this.capacidade];
        this.topo = -1;
    }

    public boolean empilhar(Chamado c) {
        if (isCheia()) {
            return false;
        }
        elementos[++topo] = c;
        return true;
    }

    public Chamado desempilhar() {
        if (isVazia()) {
            return null;
        }
        Chamado c = elementos[topo];
        elementos[topo--] = null;
        return c;
    }

    public Chamado topo() {
        if (isVazia()) {
            return null;
        }
        return elementos[topo];
    }

    public int tamanho() {
        return topo + 1;
    }

    public boolean isCheia() {
        return topo == capacidade - 1;
    }

    public boolean isVazia() {
        return topo == -1;
    }

    public void mostrar() {
        if (isVazia()) {
            System.out.println("Pilha vazia.");
            return;
        }

        System.out.println("=== Pilha de Emergência ===");
        for (int i = topo; i >= 0; i--) {
            System.out.println(elementos[i]);
        }
    }
}