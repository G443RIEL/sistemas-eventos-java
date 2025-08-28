# sistemas-eventos-java 

# Sistema de Cadastro e Notificação de Eventos (Java Console)

Este projeto foi desenvolvido em Java como parte de atividade acadêmica, seguindo o paradigma de Programação.  
O sistema permite cadastrar usuários, cadastrar eventos, consultar eventos, confirmar ou cancelar participação e salvar eventos em arquivo (`events.data`).

-------------------------------------------------------------------------------------------------------------------------------------------------------

Funcionalidades
- Cadastro de usuários (mínimo de 3 atributos).
- Cadastro de eventos (nome, endereço, categoria, horário, descrição).
- Categorias de exemplo: festas, esportes, shows, palestras, etc.
- Consulta de eventos cadastrados, ordenados por data/hora.
- Identificação de eventos já ocorridos e em andamento (usando `LocalDateTime`).
- Confirmação e cancelamento de participação em eventos.
- Persistência em arquivo de texto (`events.data`), com leitura automática ao iniciar o programa.

-------------------------------------------------------------------------------------------------------------------------------------------------------

Estrutura do Projeto
src/
├── Usuario.java
├── Evento.java
├── SistemaEventos.java
└── Main.java
events.data

-------------------------------------------------------------------------------------------------------------------------------------------------------

Diagrama de Classes

classDiagram
    class Usuario {
        - int id
        - String nome
        - String email
        - String cidade
        - List<Evento> eventosConfirmados
        + participarEvento(Evento)
        + cancelarEvento(Evento)
    }

    class Evento {
        - int id
        - String nome
        - String endereco
        - String categoria
        - LocalDateTime horario
        - String descricao
        + isOcorrendoAgora()
        + isJaPassou()
        + toDataString()
        + fromDataString()
    }

    class SistemaEventos {
        - List<Usuario> usuarios
        - List<Evento> eventos
        + cadastrarUsuario(Usuario)
        + cadastrarEvento(Evento)
        + listarEventos()
        + salvarEventosNoArquivo()
        + carregarEventosDeArquivo()
    }

    class Main {
        + main()
    }

    Usuario "1" --> "*" Evento : participa
    SistemaEventos "1" --> "*" Evento
    SistemaEventos "1" --> "*" Usuario

-------------------------------------------------------------------------------------------------------------------------------------------------------

Como o sistema foi montado!!

   A ideia do sistema nasceu a partir de um diagrama UML de classes, que mostrava os elementos principais que precisávamos para gerenciar eventos e usuários. Com base nesse desenho, fui construindo o código em Java passo a passo, sempre tentando transformar o        que estava no papel em algo funcional no console.
-------------------------------------------------------------------------------------------------------------------------------------------------------
1. Estrutura inicial

  Primeiro, pensamos em quais entidades seriam necessárias:    
  - Usuário (quem participa ou organiza eventos);
  - Evento (o que de fato vai acontecer, com título, data e local);
  - SistemaEventos (um tipo de "controlador", que organiza usuários e eventos);
  - Main (o ponto de entrada, onde o programa roda e o usuário interage).
      
Essa divisão deixou o sistema limpo e fácil de entender, porque cada classe tem a sua função.
-------------------------------------------------------------------------------------------------------------------------------------------------------     
2. Classe Usuario

  Comecei criando a classe Usuario, que guarda as informações básicas como nome e email.
  Ela também tem métodos simples de acesso (getters e setters), já que cada usuário pode ser usado em diferentes eventos.
  Essa parte é como se fosse o “cadastro de pessoas” dentro do sistema.
-------------------------------------------------------------------------------------------------------------------------------------------------------
3. Classe Evento

  Depois criei a classe Evento, que representa qualquer tipo de evento.
  Aqui coloquei atributos como: nome do evento, data, local, e a lista de participantes.
  Além disso, adicionei métodos para adicionar usuários ao evento, permitindo que as pessoas fossem inscritas. Essa foi a forma de conectar Usuário com Evento.
-------------------------------------------------------------------------------------------------------------------------------------------------------
4. Classe SistemaEventos

  Essa classe é como o “cérebro” do sistema.

  Ela mantém duas listas principais:
    - Uma de usuários cadastrados,
    - Outra de eventos criados.

  Foi aqui que implementamos funcionalidades como:
    - Cadastrar novos usuários;
    - Criar eventos;
    - Listar o que já foi criado;
    - Salvar e carregar os dados em arquivo (events.data), para que o programa lembre das informações mesmo depois de fechar.

  Esse recurso de persistência com serialização em arquivo foi essencial para o sistema não ser apenas “temporário”.
-------------------------------------------------------------------------------------------------------------------------------------------------------
5. Classe Main

Por último, criei a Main, que é o menu interativo para o usuário. Quando rodamos o programa, ela mostra opções como:
  - Cadastrar usuário,
  - Criar evento,
  - Listar eventos,
  - Inscrever participante em evento,
  - Salvar e carregar dados,
  - Sair.

Esse menu é todo baseado em texto, rodando no console, para simplificar a interação.
-------------------------------------------------------------------------------------------------------------------------------------------------------
6. Persistência de dados

  Um dos pontos mais legais foi o fato de que o sistema não “esquece” tudo quando fecha.
  Com o uso da serialização Java, salvei os objetos em um arquivo chamado events.data.
  Assim, ao abrir novamente, o sistema consegue recuperar todos os usuários e eventos criados anteriormente. Isso dá uma sensação de sistema “real”.
-------------------------------------------------------------------------------------------------------------------------------------------------------
7. Boas práticas

Durante a construção, seguimos algumas boas práticas:
  - Separação de responsabilidades (cada classe faz apenas o que é dela);
  - Uso de listas (ArrayList) para armazenar coleções de dados;
  - Um README.md explicando como rodar o sistema;
  - Arquivo .gitignore para evitar sujeira no repositório

