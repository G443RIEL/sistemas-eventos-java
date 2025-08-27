package sistemaseventos;

import java.io.*;
import java.util.*;

public class SistemaEventos {
    private List<Usuario> usuarios = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();
    private final String ARQUIVO = "events.data";

    public void cadastrarUsuario(Usuario u) { usuarios.add(u); }
    public void cadastrarEvento(Evento e) { eventos.add(e); }

    public void listarEventos() {
        eventos.sort(Comparator.comparing(Evento::getHorario));
        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            String status = e.isJaPassou() ? " (Já ocorreu)" : (e.isOcorrendoAgora() ? " (Acontecendo agora)" : "");
            System.out.println((i+1) + ". " + e + status);
        }
    }

    public List<Evento> getEventos() { return eventos; }

    // Persistência
    public void salvarEventosNoArquivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARQUIVO))) {
            for (Evento e : eventos) {
                bw.write(e.toDataString());
                bw.newLine();
            }
            System.out.println("Eventos salvos em " + ARQUIVO);
        } catch (IOException e) {
            System.out.println("Erro ao salvar eventos: " + e.getMessage());
        }
    }

    public void carregarEventosDeArquivo() {
        eventos.clear();
        File f = new File(ARQUIVO);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Evento e = Evento.fromDataString(linha);
                if (e != null) eventos.add(e);
            }
            System.out.println("Eventos carregados do arquivo.");
        } catch (IOException e) {
            System.out.println("Erro ao carregar eventos: " + e.getMessage());
        }
    }
}