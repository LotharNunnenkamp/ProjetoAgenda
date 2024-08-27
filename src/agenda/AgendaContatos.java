package agenda;

import models.Operacoes;
import services.AgendaServices;

import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;


public class AgendaContatos {
    static String[][] contatos = new String[100][6];
    static int indice = 0;

    private static void increaseIndex() {
        indice++;
    }

    private static void decreaseIndex() {
        indice++;
    }

    static public void iniciarSistema() {
        Scanner sc = new Scanner(System.in);
        Operacoes operacao = null;

        do {
            System.out.println(
                    """
                            \n##################
                            ##### AGENDA #####
                            ##################
                            """);

            System.out.println("\n>>>> Menu Contato <<<<");
            System.out.println("1 - Adicionar Contato");
            System.out.println("2 - Detalhar Contato");
            System.out.println("3 - Editar Contato");
            System.out.println("4 - Remover Contatos");
            System.out.println("5 - Listar Contatos");
            System.out.println("6 - Favoritar Contato");
            System.out.println("7 - Sair\n");
            System.out.print("Escolha uma opção: ");

            int opcao = 0;
            if (sc.hasNextInt()) {
                opcao = sc.nextInt();

                if (opcao >= 1 && opcao <= Operacoes.values().length) {
                    operacao = Operacoes.values()[opcao - 1];
                } else {
                    operacao = null;
                }
            } else {
                System.out.println("Opção inválida. Tente novamente!");
                sc.next();
                continue;
            }

            if (operacao == null) {
                System.out.println("Opção inválida. Tente novamente!");
                continue;
            }

            switch (operacao) {
                case ADICIONAR:
                    agendaAdaptavel();
                    adicionarContato(contatos, sc, indice);
                    break;
                case DETALHAR:
                    detalharContato(contatos, sc);
                    break;
                case EDITAR:
                    editarContato(contatos, sc);
                    break;
                case REMOVER:
                    removerContato(sc);
                    break;
                case LISTAR:
                    listarTodosContatos();
                    break;
                case FAVORITAR:
                    favoritarContato(contatos, sc);
                    break;
                case SAIR:
                    System.out.println("Tem certeza que deseja sair do programa?\n1 - SIM\n2 - NÃO");
                    int confimacao = sc.nextInt();
                    if (confimacao == 2) {
                        operacao = null;
                        continue;
                    }
                    System.out.println("Saindo do programa...");
                    break;
            }

        } while (operacao != Operacoes.SAIR);
    }

    private static void adicionarContato(String[][] contatos, Scanner sc, int indice) {
        try {
            System.out.println(">>>>>Adicionando Contato<<<<<");

            if (sc.hasNextLine()) {
                sc.nextLine();
            }

            System.out.print("Digite o nome: ");
            String nome = sc.nextLine();
            String telefone = AgendaServices.adicionarTelefone(contatos, sc);
            String email;
            do {
                System.out.print("Digite o E-mail: ");
                email = sc.nextLine();
            } while (!AgendaServices.validarEmail(email));
            System.out.println("Digite um telefone adicional ou pressione enter: ");
            String telefoneAdicional = sc.nextLine();
            System.out.println("Digite o endereço ou pressione enter: ");
            String endereco = sc.nextLine();

            contatos[indice][0] = nome;
            contatos[indice][1] = telefone;
            contatos[indice][2] = email;
            contatos[indice][3] = telefoneAdicional;
            contatos[indice][4] = endereco;
            contatos[indice][5] = "false";
            increaseIndex();
            System.out.println("Contato adicionado com sucesso!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erro: Tentativa de acessar um índice inválido do array de contatos.");
        } catch (InputMismatchException e) {
            System.out.println("Erro: Tipo de entrada inválido.");
            sc.next();
        } catch (Exception e) {
            System.out.println("Erro desconhecido ao adicionar contato: " + e.getMessage());
        }
    }


    private static void detalharContato(String[][] contatos, Scanner sc) {
        try {
            System.out.println(">>>>>Detalhando contato<<<<<");
            System.out.print("Digite o número de telefone do contato: ");
            String telefone = sc.next();

            for (String[] contato : contatos) {
                if (AgendaServices.checarSeContatoExiste(contatos, telefone)) {
                    System.out.println("\n-----Informações-----");
                    System.out.println("Nome: " + contato[0] + "\t|Telefone: " + contato[1] + "\t |E-mail: " + contato[2] + "\t |Telefone adicional: " + contato[3] + "\t |Endereço: " + contato[4] + "\t |Favorito: " + contato[5]);
                    break;
                } else {
                    System.out.println("Contato não encontrado!");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao detalhar contato: " + e.getMessage());
        }
    }

    private static void editarContato(String[][] contatos, Scanner sc) {
        try {
            System.out.print("Digite o número de telefone do contato: ");
            String telefone = sc.next();

            boolean contatoJaExiste = false;
            for (int i = 0; i < contatos.length; i++) {
                if (contatos[i][1] != null && contatos[i][1].equals(telefone)) {
                    System.out.println(">>>>Atualizando contato<<<<");
                    System.out.print("Novo Nome: ");
                    String novoNome = sc.next();
                    System.out.print("Novo Telefone: ");
                    String novoTelefone = sc.next();
                    System.out.print("Novo E-mail: ");
                    String novoEmail = sc.next();
                    System.out.println("Novo Telefone adicional: ");
                    String novoTelefoneAdicional = sc.next();
                    System.out.println("Novo Endereço: ");
                    String novoEndereco = sc.next();
                    System.out.printf("\nVocê está alterando os dados do contato para:" +
                            "\nNome: %s\nTelefone: %s\nE-mail: %s\nTelefone adicional: %s\nEndereço: %s\nDigite 1 para Confirmar e 2 para Cancelar: ", novoNome, novoTelefone, novoEmail, novoTelefoneAdicional, novoEndereco);
                    int confirmacao = sc.nextInt();
                    if (confirmacao == 2) {
                        System.out.println("\nOperacão Cancelada...\n");
                        iniciarSistema();
                    }
                    contatos[i][0] = novoNome;
                    contatos[i][1] = novoTelefone;
                    contatos[i][2] = novoEmail;
                    contatos[i][3] = novoTelefoneAdicional;
                    contatos[i][4] = novoEndereco;
                    System.out.println("Contato atualizado!");
                    contatoJaExiste = true;
                    break;
                }
            }
            if (!contatoJaExiste) {
                System.out.println("Contato não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao editar contato: " + e.getMessage());
        }
    }

    private static void removerContato(Scanner sc) {
        try {
            System.out.println("Digite o número de telefone do contato a ser removido: ");
            String telefone = sc.next();
            System.out.println("Tem certeza que deseja remover o contato abaixo?");
            String nomeContato = "", telefoneContato = "", emailContato = "", telefoneAdicionalContato = "", enderecoContato = "";
            for (int i = 0; i < indice; i++) {
                if (contatos[i][1].equals(telefone)) {
                    nomeContato = contatos[i][0];
                    telefoneContato = contatos[i][1];
                    emailContato = contatos[i][2];
                    telefoneAdicionalContato = contatos[i][3];
                    enderecoContato = contatos[i][4];
                }
            }
            System.out.printf("\nNome: %s\nTelefone: %s\nE-mail: %s\nTelefone adicional: %s\nEndereço: %s", nomeContato, telefoneContato, emailContato, telefoneAdicionalContato, enderecoContato);
            System.out.println("\nDigite 1 para Confirmar e 2 para Cancelar: ");
            int confirmacao = sc.nextInt();
            if (confirmacao == 2) {
                System.out.println("\nOperacão Cancelada...\n");
                iniciarSistema();
            }

            for (int i = 0; i < indice; i++) {
                if (contatos[i][1].equals(telefone)) {
                    for (int j = i; j < indice - 1; j++) {
                        contatos[j] = contatos[j + 1];
                    }
                    contatos[indice - 1] = new String[6];
                    decreaseIndex();
                    System.out.println("Contato removido com sucesso.");
                    return;
                }
            }
            System.out.println("Não encontrado.");
        } catch (Exception e) {
            System.out.println("Erro ao remover contato: " + e.getMessage());
        }
    }


    private static void listarTodosContatos() {
        System.out.println(">>>>>>> CONTATOS <<<<<<<");

        System.out.printf("%-5s %-20s %-15s %-30s %-15s %-50s %-10s\n", "Id", "Nome", "Telefone", "E-mail", "Telefone adicional", "Endereço", "Favorito");
        System.out.println("---------------------------------------------------------------");

        for (int i = 0; i < indice; i++) {
            System.out.printf("%-5d %-20s %-15s %-30s %-15s %-50s %-10s\n", i, contatos[i][0], contatos[i][1], contatos[i][2], contatos[i][3], contatos[i][4], contatos[i][5]);
        }

        System.out.println("---------------------------------------------------------------");
    }

    private static void favoritarContato(String[][] contatos, Scanner sc) {
        try {
            System.out.println("Digite o telefone do contato: ");
            String telefone = sc.next();

            for (String[] contato : contatos) {
                if (AgendaServices.checarSeContatoExiste(contatos, telefone)) {
                    System.out.println("Deseja favoritar o seguinte contato?");
                    System.out.println("Nome: " + contato[0] + "\t|Telefone: " + contato[1] + "\t |E-mail: " + contato[2] + "\t |Telefone adicional: " + contato[3] + "\t |Endereço: " + contato[4] + "\t |Favorito: " + contato[5]);
                    System.out.println("Digite 1 para SIM e 2 para NÃO");
                    String opcao = sc.next();
                    if (!Objects.equals(opcao, "1")) {
                        break;
                    }
                    System.out.println(opcao);
                    System.out.println(contato[5]);
                    contato[5] = "true";
                    System.out.println("Contato favoritado com sucesso!");
                    break;
                } else {
                    System.out.println("Contato não encontrado!");
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao detalhar contato: " + e.getMessage());
        }
    }

    private static void agendaAdaptavel() {
        if (AgendaServices.listaCheia(contatos, indice)) {
            String[][] novosContatos = new String[2 * indice][6];
            for (int i = 0; i < contatos.length; i++) {
                for (int j = 0; j < contatos[i].length; j++) {
                    novosContatos[i][j] = contatos[i][j];
                }
            }
            contatos = novosContatos;
        }
    }

}