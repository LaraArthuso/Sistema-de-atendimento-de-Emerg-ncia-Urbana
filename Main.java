import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SistemaAtendimento sistema = new SistemaAtendimento(30);

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarChamado();
                    break;
                case 2:
                    realizarAtendimento();
                    break;
                case 3:
                    concluirAtendimento();
                    break;
                case 4:
                    sistema.mostrarAbertos();
                    break;
                case 5:
                    sistema.mostrarAtivos();
                    break;
                case 6:
                    sistema.mostrarHistorico();
                    break;
                case 7:
                    mostrarEstatisticaUrgencia();
                    break;
                case 8:
                    filtrarChamadosPorNivel();
                    break;
                case 9:
                    mostrarRankingBairros();
                    break;
                case 10:
                    simularCadastros();
                    break;
                case 11:
                    if (confirmarSaida()) {
                        System.out.println("Sistema encerrado com sucesso.");
                    } else {
                        opcao = 0;
                    }
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }

            System.out.println();
        } while (opcao != 11);
    }

    public static void exibirMenu() {
        System.out.println("==============================================");
        System.out.println(" SISTEMA DE ATENDIMENTO DE EMERGÊNCIA URBANA ");
        System.out.println("==============================================");
        System.out.println("1. Cadastrar chamado");
        System.out.println("2. Realizar atendimento");
        System.out.println("3. Concluir atendimento");
        System.out.println("4. Mostrar chamados abertos");
        System.out.println("5. Mostrar atendimentos ativos");
        System.out.println("6. Mostrar histórico completo");
        System.out.println("7. Mostrar estatística dos níveis de urgência");
        System.out.println("8. Filtrar chamados por nível de urgência");
        System.out.println("9. Mostrar ranking de bairros");
        System.out.println("10. Simular cadastro automático");
        System.out.println("11. Sair");
        System.out.println("==============================================");
    }

    public static void cadastrarChamado() {
        System.out.println("\n=== Cadastro de Chamado ===");

        int id = lerInteiro("Informe o ID do chamado: ");

        if (sistema.idExiste(id)) {
            System.out.println("Erro: já existe um chamado com esse ID.");
            return;
        }

        System.out.print("Informe o bairro: ");
        String bairro = scanner.nextLine();

        System.out.print("Informe a descrição: ");
        String descricao = scanner.nextLine();

        int nivelUrgencia;
        do {
            nivelUrgencia = lerInteiro("Informe o nível de urgência (1 a 5): ");
            if (nivelUrgencia < 1 || nivelUrgencia > 5) {
                System.out.println("Erro: o nível de urgência deve estar entre 1 e 5.");
            }
        } while (nivelUrgencia < 1 || nivelUrgencia > 5);

        Chamado chamado = new Chamado(id, bairro, descricao, nivelUrgencia);

        if (sistema.cadastrarChamado(chamado)) {
            System.out.println("Chamado cadastrado com sucesso.");
        } else {
            System.out.println("Erro: não foi possível cadastrar o chamado.");
            System.out.println("A pilha ou fila correspondente pode estar cheia.");
        }
    }

    public static void realizarAtendimento() {
        System.out.println("\n=== Realizar Atendimento ===");

        Chamado chamado = sistema.realizarAtendimento();

        if (chamado == null) {
            System.out.println("Não há chamados aguardando atendimento.");
        } else {
            System.out.println("Chamado encaminhado para atendimento:");
            System.out.println(chamado);
        }
    }

    public static void concluirAtendimento() {
        System.out.println("\n=== Concluir Atendimento ===");

        if (sistema.getAtivos().isEmpty()) {
            System.out.println("Não há atendimentos ativos no momento.");
            return;
        }

        System.out.println("Lista de atendimentos ativos:");
        sistema.mostrarAtivos();

        int id = lerInteiro("Informe o ID do chamado que deseja concluir: ");

        if (sistema.concluirAtendimento(id)) {
            System.out.println("Atendimento concluído com sucesso.");
        } else {
            System.out.println("Erro: o ID informado não está na lista de atendimentos ativos.");
        }
    }

    public static void mostrarEstatisticaUrgencia() {
        System.out.println("\n=== Estatística dos Níveis de Urgência ===");
        sistema.mostrarEstatisticaUrgencia();
    }

    public static void filtrarChamadosPorNivel() {
        System.out.println("\n=== Filtrar Chamados por Nível de Urgência ===");

        int nivel;
        do {
            nivel = lerInteiro("Informe o nível de urgência desejado (1 a 5): ");
            if (nivel < 1 || nivel > 5) {
                System.out.println("Erro: o nível deve estar entre 1 e 5.");
            }
        } while (nivel < 1 || nivel > 5);

        sistema.mostrarChamadosPorNivel(nivel);
    }

    public static void mostrarRankingBairros() {
        System.out.println("\n=== Ranking de Bairros ===");
        sistema.mostrarRankingBairros();
    }

    public static void simularCadastros() {
        System.out.println("\n=== Simulação de Cadastro Automático ===");

        Chamado[] chamadosSimulados = {
            new Chamado(101, "Centro", "Queda de árvore", 2),
            new Chamado(102, "Mooca", "Incêndio em residência", 5),
            new Chamado(103, "Tatuapé", "Falta de energia", 3),
            new Chamado(104, "Butantã", "Vazamento de gás", 4),
            new Chamado(105, "Pinheiros", "Buraco na via", 1),
            new Chamado(106, "Lapa", "Desabamento parcial", 5),
            new Chamado(107, "Ipiranga", "Acidente de trânsito", 4),
            new Chamado(108, "Santana", "Enchente", 5),
            new Chamado(109, "Vila Mariana", "Semáforo apagado", 2),
            new Chamado(110, "Liberdade", "Curto-circuito em poste", 4)
        };

        int inseridos = 0;

        for (Chamado chamado : chamadosSimulados) {
            if (!sistema.idExiste(chamado.getId())) {
                if (sistema.cadastrarChamado(chamado)) {
                    inseridos++;
                }
            }
        }

        System.out.println(inseridos + " chamados foram cadastrados com sucesso na simulação.");
    }

    public static boolean confirmarSaida() {
        System.out.print("Deseja realmente sair do sistema? (s/n): ");
        String resposta = scanner.nextLine().trim().toLowerCase();
        return resposta.equals("s");
    }

    public static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }
}