package sistemaseventos;

import java.time.LocalDateTime;
import java.util.Objects;

public class Evento {
    private int id;
    private String nome;
    private String endereco;
    private String categoria;
    private LocalDateTime horario;
    private String descricao;

    public Evento(int id, String nome, String endereco, String categoria, LocalDateTime horario, String descricao) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.categoria = categoria;
        this.horario = horario;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public LocalDateTime getHorario() { return horario; }

    public boolean isOcorrendoAgora() {
        return LocalDateTime.now().isAfter(horario.minusMinutes(30)) && 
               LocalDateTime.now().isBefore(horario.plusHours(2));
    }

    public boolean isJaPassou() {
        return LocalDateTime.now().isAfter(horario.plusHours(2));
    }

    @Override
    public String toString() {
        return nome + " - " + categoria + " - " + horario + " - " + endereco;
    }

    // Para salvar em arquivo
    public String toDataString() {
        return id + ";" + nome + ";" + endereco + ";" + categoria + ";" + horario + ";" + descricao;
    }

    // Para carregar do arquivo
    public static Evento fromDataString(String linha) {
        try {
            String[] p = linha.split(";");
            int id = Integer.parseInt(p[0]);
            String nome = p[1];
            String endereco = p[2];
            String categoria = p[3];
            LocalDateTime horario = LocalDateTime.parse(p[4]);
            String descricao = p[5];
            return new Evento(id, nome, endereco, categoria, horario, descricao);
        } catch (Exception e) {
            System.out.println("Erro ao carregar evento: " + linha);
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return id == evento.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}