package sistemaseventos;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String cidade;
    private final List<Evento> eventosConfirmados = new ArrayList<>();

    public Usuario(int id, String nome, String email, String cidade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cidade = cidade;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getCidade() { return cidade; }
    public List<Evento> getEventosConfirmados() { return eventosConfirmados; }

    public void participarEvento(Evento evento) {
        if (!eventosConfirmados.contains(evento)) {
            eventosConfirmados.add(evento);
            System.out.println(nome + " confirmado no evento: " + evento.getNome());
        } else {
            System.out.println(nome + " já está confirmado no evento: " + evento.getNome());
        }
    }

    public void cancelarEvento(Evento evento) {
        if (eventosConfirmados.remove(evento)) {
            System.out.println(nome + " cancelou presença no evento: " + evento.getNome());
        } else {
            System.out.println(nome + " não estava confirmado no evento: " + evento.getNome());
        }
    }
}