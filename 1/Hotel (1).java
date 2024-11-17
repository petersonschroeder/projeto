

// Bibliotecas
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;

class Quartos {
    private Boolean pett;
    private int numero;
    private boolean temBanheiro;
    private int quantidadeCamas;
    private double valorDiaria;
    private boolean disponivel;
    private int nivelConforto;

    // Construtor
    public Quartos(boolean permitidoPet, int numero, boolean temBanheiro, int quantidadeCamas, double valorDiaria, boolean disponivel, int nivelConforto) {
        this.pett = permitidoPet;
        this.numero = numero;
        this.temBanheiro = temBanheiro;
        this.quantidadeCamas = quantidadeCamas;
        this.valorDiaria = valorDiaria;
        this.disponivel = disponivel;
        this.nivelConforto = nivelConforto;
    }

    // Getters e Setters
    public Boolean getPett() {
        return pett;
    }

    public int getNumero() {
        return numero;
    }

    public boolean isTemBanheiro() {
        return temBanheiro;
    }

    public int getQuantidadeCamas() {
        return quantidadeCamas;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public int getNivelConforto() {
        return nivelConforto;
    }

    // Método para exibir as informações de um quarto
    public String exibirInfo() {
        return "Número: " + numero +
               ", Pets: " + pett +
               ", Banheiro: " + (temBanheiro ? "Sim" : "Não") +
               ", Camas: " + quantidadeCamas +
               ", Diária: R$ " + valorDiaria +
               ", Disponível: " + (disponivel ? "Sim" : "Não") +
               ", Nível de Conforto: " + nivelConforto;
    }

    // Método para criar um array com 20 quartos e valores fixos
    public static Quartos[] criarQuartos() {
        Quartos[] quartos = new Quartos[20];
        
        quartos[0] = new Quartos(true, 101, true, 2, 150.0, true, 5);
        quartos[1] = new Quartos(false, 102, false, 1, 100.0, true, 3);
        quartos[2] = new Quartos(true, 103, true, 3, 200.0, true, 4);
        quartos[3] = new Quartos(false, 104, true, 2, 180.0, false, 5);
        quartos[4] = new Quartos(true, 105, false, 1, 120.0, true, 3);
        quartos[5] = new Quartos(true, 106, true, 2, 220.0, false, 5);
        quartos[6] = new Quartos(false, 107, false, 1, 90.0, true, 2);
        quartos[7] = new Quartos(true, 108, true, 3, 250.0, false, 4);
        quartos[8] = new Quartos(false, 109, false, 1, 95.0, true, 3);
        quartos[9] = new Quartos(true, 110, true, 2, 170.0, true, 5);
        quartos[10] = new Quartos(false, 111, true, 1, 130.0, false, 3);
        quartos[11] = new Quartos(true, 112, false, 2, 160.0, true, 4);
        quartos[12] = new Quartos(true, 113, true, 3, 210.0, true, 5);
        quartos[13] = new Quartos(false, 114, false, 1, 100.0, false, 2);
        quartos[14] = new Quartos(true, 115, true, 2, 180.0, true, 4);
        quartos[15] = new Quartos(false, 116, false, 1, 110.0, true, 3);
        quartos[16] = new Quartos(true, 117, true, 3, 230.0, false, 5);
        quartos[17] = new Quartos(false, 118, false, 1, 105.0, true, 2);
        quartos[18] = new Quartos(true, 119, true, 2, 190.0, true, 4);
        quartos[19] = new Quartos(false, 120, true, 1, 125.0, true, 3);

        return quartos;
    }
}

    class DatabaseConnection {
    // URL do banco de dados, usuário e senha
    private static final String URL = "jdbc:mysql://localhost:3306/BD_Hotel"; 
    private static final String USER = "Lucas";
    private static final String PASSWORD = "12345";

    private static Connection connection;

    public static void conectar() {
        try {
            // Carregar o driver JDBC explicitamente
            if (connection == null || connection.isClosed()) {
                // Tentando carregar o driver manualmente
                Class.forName("com.mysql.cj.jdbc.Driver"); // Driver do MySQL 8.x
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão estabelecida com sucesso!");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver não encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static void fecharConexao() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexão fechada com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}

public class Hotel {
    private static Quartos[] quartos = Quartos.criarQuartos(); // Usa o método da classe Quartos para inicializar os 20 quartos
    private static int totalQuartos = quartos.length; // Total de quartos disponíveis inicialmente é 20

    public static void main(String[] args) {
        DatabaseConnection.conectar(); // Estabelece a conexão com o banco de dados
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu de Seleção:");
            System.out.println("\n1 - Adicionar um novo Quarto");
            System.out.println("2 - Listar Quartos");
            System.out.println("3 - Pesquisar Quarto");
            System.out.println("4 - Realizar Reserva");
            System.out.println("5 - Consultar Disponibilidade");
            System.out.println("6 - Listar melhores quartos por estrelas");
            System.out.println("7 - Listar melhores quartos por valor");
            System.out.println("0 - Sair");
            System.out.print("\nEscolha uma opção: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    adicionarQuarto(scanner);
                    break;
                case 2:
                    listarQuartos();
                    break;
                case 3:
                    pesquisarQuarto(scanner);
                    break;
                case 4:
                    realizarReserva(scanner);
                    break;
                case 5:
                    realizarConsulta(scanner);
                    break;
                case 6:
                    listarQuartosPorEstrelas();
                    break;
                case 7:
                    listarQuartosPorValor();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
        DatabaseConnection.fecharConexao(); // Fecha a conexão com o banco de dados
    }

    private static void adicionarQuarto(Scanner scanner) {
        if (totalQuartos >= quartos.length) {
            System.out.println("\nNão é possível adicionar mais quartos.");
            return;
        }
    
        System.out.print("Digite o número do quarto: ");
        int numero = scanner.nextInt();
        scanner.nextLine(); 
    
        // Verifica se o número do quarto já existe
        for (int i = 0; i < totalQuartos; i++) {
            if (quartos[i] != null && quartos[i].getNumero() == numero) {
                System.out.println("Número do quarto já existe. Tente outro.");
                return;
            }
        }
    
        System.out.print("Permitido PET? (Sim/Não): ");
        String pett = scanner.nextLine().trim().toLowerCase();
        boolean permitidoPet = pett.equals("sim");
    
        System.out.print("Tem banheiro? (true/false): ");
        boolean temBanheiro = scanner.nextBoolean();
        scanner.nextLine(); 
    
        System.out.print("Quantidade de camas: ");
        int quantidadeCamas = scanner.nextInt();
        scanner.nextLine(); 
    
        System.out.print("Valor da diária: ");
        double valorDiaria = scanner.nextDouble();
        scanner.nextLine(); 
    
        System.out.print("Disponível? (true/false): ");
        boolean disponivel = scanner.nextBoolean();
        scanner.nextLine(); 
    
        System.out.print("Nível de conforto (1 a 5): ");
        int nivelConforto = scanner.nextInt();
        scanner.nextLine(); 
    
        // Validação do nível de conforto
        if (nivelConforto < 1 || nivelConforto > 5) {
            System.out.println("Nível de conforto deve estar entre 1 e 5.");
            return;
        }
    
        quartos[totalQuartos] = new Quartos(permitidoPet, numero, temBanheiro, quantidadeCamas, valorDiaria, disponivel, nivelConforto);
        totalQuartos++;
        System.out.println("Quarto adicionado com sucesso!");
    }

    private static void listarQuartos() {
        System.out.println("\nListando quartos:");
        System.out.println("");
        for (int i = 0; i < totalQuartos; i++) {
            mostrarInfoResumida(quartos[i]);
        }
    }

    private static void pesquisarQuarto(Scanner scanner) {
        System.out.print("\nDigite o número do quarto que deseja pesquisar: ");
        int numero = scanner.nextInt();
        
        boolean encontrado = false;
        for (int i = 0; i < totalQuartos; i++) {
            if (quartos[i] != null && quartos[i].getNumero() == numero) {
                mostrarInfoResumida(quartos[i]);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("\nQuarto número " + numero + " não encontrado.");
        }
    }

    private static void realizarReserva(Scanner scanner) {
    System.out.print("\nDigite o número do quarto que deseja reservar: ");
    int numero;
    
    try {
        numero = scanner.nextInt();
    } catch (InputMismatchException e) { // Validação de número
        System.out.println("\nPor favor, insira um número válido.");
        scanner.next(); 
        return; 
    }
    scanner.nextLine(); 

    System.out.print("\nDigite seu nome: ");
    String nomeCliente = scanner.nextLine().trim();
    
    // Validação do nome do cliente
    if (nomeCliente.isEmpty()) {
        System.out.println("\nO nome do cliente não pode estar vazio !!!");
        return;
    }

    boolean encontrado = false;
    for (int i = 0; i < totalQuartos; i++) {
        if (quartos[i] != null && quartos[i].getNumero() == numero) {
            if (quartos[i].isDisponivel()) {
                // Atualiza a disponibilidade do quarto
                quartos[i].setDisponivel(false);
                System.out.println("\nReserva realizada com sucesso para o quarto número " + numero);

                // Inserir a reserva no banco de dados
                try (Connection conn = DatabaseConnection.getConnection()) {
                    String sql = "INSERT INTO reservas (Numero, Nome_Cliente) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, numero);
                        pstmt.setString(2, nomeCliente);
                        pstmt.executeUpdate();
                        System.out.println("\nReserva registrada no banco de dados com sucesso!");
                    } catch (SQLException e) {
                        System.err.println("\nErro ao inserir reserva no banco de dados: " + e.getMessage());
                    }
                } catch (SQLException e) {
                    System.err.println("\nErro ao conectar ao banco de dados: " + e.getMessage());
                }
            } else {
                System.out.println("\nO quarto número " + numero + " já está reservado.");
            }
            encontrado = true;
            break;
        }
    }

    if (!encontrado) {
        System.out.println("\nQuarto número " + numero + " não encontrado.");
    }
    }

    private static void realizarConsulta(Scanner scanner) { 
        System.out.print("\nDigite o número do quarto que deseja consultar a disponibilidade: ");
        int numero = scanner.nextInt();
    
        boolean encontrado = false;
        for (int i = 0; i < totalQuartos; i++) {
            if (quartos[i] != null && quartos[i].getNumero() == numero) {
                if (quartos[i].isDisponivel()) {
                    System.out.println("\nQuarto número " + numero + " está disponível.");
                } else {
                    System.out.println("\nQuarto número " + numero + " está indisponível.");
                }
                encontrado = true;
                break;
            }
        }
    
        if (!encontrado) {
            System.out.println("\nQuarto número " + numero + " não encontrado.");
        }
    }

    private static void listarQuartosPorEstrelas() {
        Quartos[] quartosOrdenados = Arrays.copyOf(quartos, totalQuartos);
        Arrays.sort(quartosOrdenados, Comparator.comparingInt(Quartos::getNivelConforto).reversed());
    
        System.out.println("\nListando quartos ordenados por nível de conforto (estrelas):");
        if (quartosOrdenados.length == 0) {
            System.out.println("\nNenhum quarto disponível.");
        } else {
            for (Quartos quarto : quartosOrdenados) {
                if (quarto != null) { // Verifica se o quarto não é nulo
                    mostrarInfoResumida(quarto);
                }
            }
        }
    }
    
    private static void listarQuartosPorValor() {
        Quartos[] quartosOrdenados = Arrays.copyOf(quartos, totalQuartos);
        Arrays.sort(quartosOrdenados, Comparator.comparingDouble(Quartos::getValorDiaria));
    
        System.out.println("\nListando quartos ordenados por valor da diária:");
        if (quartosOrdenados.length == 0) {
            System.out.println("\nNenhum quarto disponível.");
        } else {
            for (Quartos quarto : quartosOrdenados) {
                if (quarto != null) { // Verifica se o quarto não é nulo
                    mostrarInfoResumida(quarto);
                }
            }
        }
    }
    
    private static void mostrarInfoResumida(Quartos quarto) {
        if (quarto != null) { // Verifica se o quarto não é nulo
            System.out.println(quarto.exibirInfo());
        }
    }
}