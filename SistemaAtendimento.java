import java.util.ArrayList;

public class SistemaAtendimento {
    private PilhaEmergencia pilhaEmergencia;
    private FilaChamados filaComum;
    private ArrayList<Chamado> historico;
    private ArrayList<Chamado> ativos;

    public SistemaAtendimento(int capacidadePilhaFila) {
        pilhaEmergencia = new PilhaEmergencia(capacidadePilhaFila);
        filaComum = new FilaChamados(capacidadePilhaFila);
        historico = new ArrayList<>(50);
        ativos = new ArrayList<>();
    }

    public boolean idExiste(int id) {
        for (Chamado c : historico) {
            if (c.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean cadastrarChamado(Chamado chamado) {
        if (idExiste(chamado.getId())) {
            return false;
        }

        boolean inseriu;

        if (chamado.getNivelUrgencia() >= 4) {
            inseriu = pilhaEmergencia.empilhar(chamado);
        } else {
            inseriu = filaComum.enfileirar(chamado);
        }

        if (inseriu) {
            historico.add(chamado);
        }

        return inseriu;
    }

    public Chamado realizarAtendimento() {
        Chamado chamado;

        if (!pilhaEmergencia.isVazia()) {
            chamado = pilhaEmergencia.desempilhar();
        } else if (!filaComum.isVazia()) {
            chamado = filaComum.desenfileirar();
        } else {
            return null;
        }

        chamado.setStatus(StatusChamado.EM_ATENDIMENTO);
        ativos.add(chamado);
        return chamado;
    }

    public boolean concluirAtendimento(int id) {
        Chamado chamadoAtivo = null;

        for (int i = 0; i < ativos.size(); i++) {
            if (ativos.get(i).getId() == id) {
                chamadoAtivo = ativos.remove(i);
                break;
            }
        }

        if (chamadoAtivo == null) {
            return false;
        }

        for (Chamado c : historico) {
            if (c.getId() == id) {
                c.setStatus(StatusChamado.FINALIZADO);
                return true;
            }
        }

        return false;
    }

    public ArrayList<Chamado> getHistorico() {
        return historico;
    }

    public ArrayList<Chamado> getAtivos() {
        return ativos;
    }

    public void mostrarAbertos() {
        boolean encontrou = false;

        for (Chamado c : historico) {
            if (c.getStatus() == StatusChamado.ABERTO) {
                System.out.println(c);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Não há chamados abertos.");
        }
    }

    public void mostrarAtivos() {
        if (ativos.isEmpty()) {
            System.out.println("Não há atendimentos ativos.");
            return;
        }

        for (Chamado c : ativos) {
            System.out.println(c);
        }
    }

    public void mostrarHistorico() {
        if (historico.isEmpty()) {
            System.out.println("Histórico vazio.");
            return;
        }

        for (Chamado c : historico) {
            System.out.println(c);
        }
    }

    public void mostrarEstatisticaUrgencia() {
        int nivel1 = 0;
        int nivel2 = 0;
        int nivel3 = 0;
        int nivel4 = 0;
        int nivel5 = 0;

        for (Chamado c : historico) {
            switch (c.getNivelUrgencia()) {
                case 1:
                    nivel1++;
                    break;
                case 2:
                    nivel2++;
                    break;
                case 3:
                    nivel3++;
                    break;
                case 4:
                    nivel4++;
                    break;
                case 5:
                    nivel5++;
                    break;
            }
        }

        System.out.println("=== Estatística por Nível de Urgência ===");
        System.out.println("Nível 1: " + nivel1 + " chamado(s)");
        System.out.println("Nível 2: " + nivel2 + " chamado(s)");
        System.out.println("Nível 3: " + nivel3 + " chamado(s)");
        System.out.println("Nível 4: " + nivel4 + " chamado(s)");
        System.out.println("Nível 5: " + nivel5 + " chamado(s)");
    }

    public void mostrarChamadosPorNivel(int nivel) {
        int contador = 0;

        for (Chamado c : historico) {
            if (c.getNivelUrgencia() == nivel) {
                System.out.println(c);
                contador++;
            }
        }

        if (contador == 0) {
            System.out.println("Não há chamados com nível de urgência " + nivel + ".");
        }else {
        System.out.println("Total: " + contador + " chamado(s)");
        }
    }

    public void mostrarRankingBairros() {
        if (historico.isEmpty()) {
            System.out.println("Não há chamados registrados para gerar ranking.");
            return;
        }

        ArrayList<String> bairros = new ArrayList<>();
        ArrayList<Integer> quantidades = new ArrayList<>();

        for (Chamado c : historico) {
            String bairroAtual = c.getBairro();
            int posicao = -1;

            for (int i = 0; i < bairros.size(); i++) {
                if (bairros.get(i).equalsIgnoreCase(bairroAtual)) {
                    posicao = i;
                    break;
                }
            }

            if (posicao == -1) {
                bairros.add(bairroAtual);
                quantidades.add(1);
            } else {
                quantidades.set(posicao, quantidades.get(posicao) + 1);
            }
        }

        for (int i = 0; i < quantidades.size() - 1; i++) {
            for (int j = 0; j < quantidades.size() - 1 - i; j++) {
                if (quantidades.get(j) < quantidades.get(j + 1)) {
                    int tempQtd = quantidades.get(j);
                    quantidades.set(j, quantidades.get(j + 1));
                    quantidades.set(j + 1, tempQtd);

                    String tempBairro = bairros.get(j);
                    bairros.set(j, bairros.get(j + 1));
                    bairros.set(j + 1, tempBairro);
                }
            }
        }

        System.out.println("=== Ranking de Bairros com Mais Chamados ===");
        for (int i = 0; i < bairros.size(); i++) {
            System.out.println((i + 1) + "º - " + bairros.get(i) + ": " + quantidades.get(i) + " chamado(s)");
        }
    }
}