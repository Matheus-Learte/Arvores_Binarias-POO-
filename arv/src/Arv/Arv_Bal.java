package Arv;

import java.util.Arrays;

public class Arv_Bal extends Arv_bin {

    Arv_Bal(int tamanho) {
        super(tamanho);
    }

    private int quantNo(String[] arv, int i) {
        if (i >= arv.length || arv[i] == null) {
            return 0;
        }

        int esq = this.quantNo(arv, 2 * i + 1);
        int dir = this.quantNo(arv, 2 * i + 2);

        return esq + dir + 1;
    }

    private boolean isBalanced(String[] arv, int i) {
        if (i >= arv.length || arv[i] == null) {
            return true;
        }

        int esq = this.quantNo(arv, 2 * i + 1);
        int dir = this.quantNo(arv, 2 * i + 2);

        if (Math.abs(esq - dir) > 1) {
            return false;
        }

        return this.isBalanced(arv, 2 * i + 1) && this.isBalanced(arv, 2 * i + 2);
    }

    private void rebuild(String[] arv, int i, int j) {
        if (i > j) {
            return;
        }
        int meio = (i + j) / 2;

        if (arv[meio] != null) {
            super.insert(arv[meio]);
        }
        this.rebuild(arv, i, meio - 1);
        this.rebuild(arv, meio + 1, j);
    }

    @Override
    public boolean insert(String value) {
        if (value != null) {
            boolean res = super.insert(value);

            String[] arv = this.getHeap();

            if (!this.isBalanced(arv, 0)) {
                int len = this.len();
                String[] aux = new String[len];
                int i = 0;
                int j = 0;

                while (len > 0) {
                    if (arv[i] != null) {
                        aux[j] = arv[i];
                        len--;
                        j++;
                    }
                    i++;
                }
                Arrays.sort(aux);
                Arrays.fill(arv, null);
                this.setLen(0);

                this.rebuild(aux, 0, aux.length - 1);
            }
            return res;
        }
        return false;
    }

    @Override
    public boolean remove(String value) {
        if (value != null) {
            boolean result = super.remove(value);

            if (result) {
                String[] arv = this.getHeap();

                if (!this.isBalanced(arv, 0)) {
                    int len = this.len();
                    String[] aux = new String[len];
                    int i = 0, j = 0;

                    while (len > 0) {
                        if (arv[i] != null) {
                            aux[j] = arv[i];
                            len--;
                            j++;
                        }
                        i++;
                    }
                    Arrays.sort(aux);
                    Arrays.fill(arv, null);
                    this.setLen(0);

                    this.rebuild(aux, 0, aux.length - 1);
                }
            }
            return result;
        }
        return false;
    }
}
