package sistemaseventos;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SistemaEventos sistema = new SistemaEventos();

        // Carrega os eventos salvos
        sistema.carregarEventosDeArquivo();

        Usuario usuario = new Usuario(1, "Gabriel", "gabriel@email.com", "São Paulo");
        sistema.cadastrarUsuario(usuario);

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Cadastrar evento");
            System.out.println("2. Listar eventos");
            System.out.println("3. Participar de evento");
            System.out.println("4. Cancelar participação");
            System.out.println("5. Meus eventos");
            System.out.println("0. Sair");
            int op = sc.nextInt(); sc.nextLine();

            switch (op) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Endereço: ");
                    String endereco = sc.nextLine();
                    System.out.print("Categoria: ");
                    String categoria = sc.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = sc.nextLine();
                    System.out.print("Ano-Mês-Dia Hora:Minuto -> ");
                    String dt = sc.nextLine();
                    try {
                        LocalDateTime data = LocalDateTime.parse(dt.replace(" ", "T"));
                        Evento evento = new Evento(
                            sistema.getEventos().size() + 1,
                            nome, endereco, categoria, data, descricao
                        );
                        sistema.cadastrarEvento(evento);
                        sistema.salvarEventosNoArquivo();
                    } catch (DateTimeParseException ex) {
                        System.out.println("Data e hora inválidas! Use o formato: yyyy-MM-dd HH:mm");
                    }
                    break;

                case 2:
                    sistema.listarEventos();
                    break;

                case 3:
                    sistema.listarEventos();
                    System.out.print("Qual evento participar? ");
                    int idx = sc.nextInt() - 1;
                    sc.nextLine();
                    if (idx >= 0 && idx < sistema.getEventos().size()) {
                        usuario.participarEvento(sistema.getEventos().get(idx));
                    } else {
                        System.out.println("Evento inválido.");
                    }
                    break;

                case 4:
                    List<Evento> confirmados = usuario.getEventosConfirmados();
                    for (int i = 0; i < confirmados.size(); i++) {
                        System.out.println((i + 1) + ". " + confirmados.get(i));
                    }
                    System.out.print("Qual cancelar? ");
                    int idc = sc.nextInt() - 1;
                    sc.nextLine();
                    if (idc >= 0 && idc < confirmados.size()) {
                        usuario.cancelarEvento(confirmados.get(idc));
                    } else {
                        System.out.println("Evento inválido.");
                    }
                    break;

                case 5:
                    for (Evento ev : usuario.getEventosConfirmados()) {
                        System.out.println(ev);
                    }
                    break;

                case 0:
                    sistema.salvarEventosNoArquivo();
                    rodando = false;
                    System.out.println("Saindo do sistema. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }
}