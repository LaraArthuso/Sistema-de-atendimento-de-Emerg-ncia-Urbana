public class Chamado {
    private int id;
    private String bairro;
    private String descricao;
    private int nivelUrgencia;
    private StatusChamado status;

    public Chamado(int id, String bairro, String descricao, int nivelUrgencia) {
        this.id = id;
        this.bairro = bairro;
        this.descricao = descricao;
        this.nivelUrgencia = nivelUrgencia;
        this.status = StatusChamado.ABERTO;
    }

    public int getId() {
        return id;
    }

    public String getBairro() {
        return bairro;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Chamado{" +
                "id=" + id +
                ", bairro='" + bairro + '\'' +
                ", descricao='" + descricao + '\'' +
                ", nivelUrgencia=" + nivelUrgencia +
                ", status=" + status +
                '}';
    }
}