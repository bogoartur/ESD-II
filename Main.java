

import java.util.Scanner;

public class Main {

    public static Node root;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String entrada = scanner.nextLine();
            if (entrada.startsWith("I ")) {
                String letra = entrada.substring(2);
                inserir(root, letra);
            } else if (entrada.equals("INFIXA")) {
                imprimirInfixa(root);
                System.out.println();
            } else if (entrada.equals("PREFIXA")) {
                imprimirPrefixa(root);
                System.out.println();
            } else if (entrada.equals("POSFIXA")) {
                imprimirPosfixa(root);
                System.out.println();
            } else if (entrada.startsWith("P ")) {
                String letra = entrada.substring(2);
                System.out.println(letra + " " + pesquisar(root, letra));
            }
        }
        scanner.close();
    }

    public static void inserir(Node no, String letra) {
        root = inserirRecursivamente(no, letra);
    }

    public static Node inserirRecursivamente(Node no, String letra) {
        if (no == null) {
            return new Node(letra, null, null);
        }

        int comp = letra.compareTo(no.getLetra());

        if (comp < 0) {
            no.setEsquerdo(inserirRecursivamente(no.getEsquerdo(), letra));
        } else if (comp > 0) {
            no.setDireito(inserirRecursivamente(no.getDireito(), letra));
        }
        return no;
    }

    public static String pesquisar(Node no, String letra) {
        if (no == null) return "nao existe";

        int comp = letra.compareTo(no.getLetra());

        if (comp == 0) return "existe";

        if (comp < 0) return pesquisar(no.getEsquerdo(), letra);
        return pesquisar(no.getDireito(), letra);
    }

    public static void imprimirInfixa(Node no) {

        if (no == null) return;

        imprimirInfixaAux(no, new StringBuilder());
    }

    private static void imprimirInfixaAux(Node no, StringBuilder sb) {
        if (no == null) return;
        imprimirInfixaAux(no.getEsquerdo(), sb);
        if (sb.length() > 0) sb.append(" ");
        sb.append(no.getLetra());
        imprimirInfixaAux(no.getDireito(), sb);
        if (sb.length() > 0 && no == root) System.out.print(sb.toString());
    }

    public static void imprimirPrefixa(Node no) {
        if (no == null) return;
        imprimirPrefixaAux(no, new StringBuilder());
    }

    private static void imprimirPrefixaAux(Node no, StringBuilder sb) {
        if (no == null) return;
        if (sb.length() > 0) sb.append(" ");
        sb.append(no.getLetra());
        imprimirPrefixaAux(no.getEsquerdo(), sb);
        imprimirPrefixaAux(no.getDireito(), sb);
        if (sb.length() > 0 && no == root) System.out.print(sb.toString());
    }

    public static void imprimirPosfixa(Node no) {
        if (no == null) return;
        imprimirPosfixaAux(no, new StringBuilder());
    }

    private static void imprimirPosfixaAux(Node no, StringBuilder sb) {
        if (no == null) return;
        imprimirPosfixaAux(no.getEsquerdo(), sb);
        imprimirPosfixaAux(no.getDireito(), sb);
        if (sb.length() > 0) sb.append(" ");
        sb.append(no.getLetra());
        if (sb.length() > 0 && no == root) System.out.print(sb.toString());
    }

    static class Node {
        private String letra;
        private Node esquerdo;
        private Node direito;
        public Node(String letra, Node esquerdo, Node direito) {
            this.letra = letra;
            this.esquerdo = esquerdo;
            this.direito = direito;
        }
        public String getLetra() {
            return letra;
        }
        public void setLetra(String letra) {
            this.letra = letra;
        }
        public Node getEsquerdo() {
            return esquerdo;
        }
        public void setEsquerdo(Node esquerdo) {
            this.esquerdo = esquerdo;
        }
        public Node getDireito() {
            return direito;
        }
        public void setDireito(Node direito) {
            this.direito = direito;
        }
    }
}