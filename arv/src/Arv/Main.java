/*
 * -------------------------------------------------------------------
 * Nome : Matheus Guilherme Ferreira Mendonça Learte   N°USP: 15522362
 * -------------------------------------------------------------------
 */
package Arv;

import java.util.Scanner;

public class Main {
    public static void main(String[] agrs) {
        Scanner read = new Scanner(System.in);
        Arv_bin bin = new Arv_bin(1000);
        Arv_Bal bal = new Arv_Bal(1000);
        Avl avl = new Avl(1000);

        while (read.hasNext()) {
            String enter = read.nextLine();
            String[] aux = enter.split(" ");

            if (aux[0].equals("i")) {
                bin.insert(aux[1]);
                avl.insert(aux[1]);
                bal.insert(aux[1]);
            } else if (aux[0].equals("d")) {
                bin.remove(aux[1]);
                avl.remove(aux[1]);
                bal.remove(aux[1]);
            }
        }
        System.out.println(bin);
        System.out.println(bal);
        System.out.println(avl);

        read.close();
    }
}