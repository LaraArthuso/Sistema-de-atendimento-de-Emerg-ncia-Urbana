public class FilaChamados {
    private Chamado[] dados;
    private int inicio;
    private int fim;
    private int quantidade;

    public FilaChamados(int capacidade) {
        dados = new Chamado[capacidade];
        inicio = 0;
        fim = 0;
        quantidade = 0;
    }

    public boolean isVazia() {
        return quantidade == 0;
    }

    public boolean isCheia() {
        return quantidade == dados.length;
    }

    public boolean enfileirar(Chamado chamado) {
        if (isCheia()) {
            return false;
        }
        dados[fim] = chamado;
        fim = (fim + 1) % dados.length;
        quantidade++;
        return true;
    }

    public Chamado desenfileirar() {
        if (isVazia()) {
            return null;
        }
        Chamado removido = dados[inicio];
        dados[inicio] = null;
        inicio = (inicio + 1) % dados.length;
        quantidade--;
        return removido;
    }

    public Chamado peek() {
        if (isVazia()) {
            return null;
        }
        return dados[inicio];
    }

    public int size() {
        return quantidade;
    }

    public void mostrar() {
        if (isVazia()) {
            System.out.println("Fila vazia.");
            return;
        }

        System.out.println("Fila de chamados comuns:");
        int pos = inicio;
        for (int i = 0; i < quantidade; i++) {
            System.out.println(dados[pos]);
            pos = (pos + 1) % dados.length;
        }
    }
}