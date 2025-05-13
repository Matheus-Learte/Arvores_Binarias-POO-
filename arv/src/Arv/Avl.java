package Arv;

import java.util.Arrays;

public class Avl extends Arv_bin {
    private Integer[] Fb;

    public Avl(int capacity) {
        super(capacity);
        Fb = new Integer[capacity];
        Arrays.fill(Fb, 0);
    }

    private void Calc_fb(String[] arv, int i) {
        while (i >= 0) {
            Fb[i] = this.alt(arv, 2 * i + 1) - this.alt(arv, 2 * i + 2);
            i = (i % 2 == 0) ? (i - 2) / 2 : (i - 1) / 2;
        }
    }

    private int alt(String[] arv, int no) {
        if (no >= arv.length || arv[no] == null)
            return -1;
        int dir = this.alt(arv, 2 * no + 1);
        int esq = this.alt(arv, 2 * no + 2);

        return 1 + Math.max(dir, esq);
    }

    private void rot_dir(String[] arv, int i) {
        int esq = 2 * i + 1;
        if (esq >= arv.length || arv[esq] == null)
            return;

        String temp = arv[i];
        arv[i] = arv[esq];
        arv[esq] = null;

        int newRight = 2 * i + 2;
        this.moverSubArvoreBaixo(newRight, 2 * newRight + 2);
        arv[newRight] = temp;

        this.moverSubArvoreSobe(2 * esq + 2, 2 * newRight + 1);
        this.moverSubArvoreSobe(2 * esq + 1, 2 * i + 1);

        this.Calc_fb(arv, newRight);
        this.Calc_fb(arv, i);
    }

    private void rot_esq(String[] arv, int i) {
        int dir = 2 * i + 2;
        if (dir >= arv.length || arv[dir] == null)
            return;

        String temp = arv[i];
        arv[i] = arv[dir];
        arv[dir] = null;

        int newLeft = 2 * i + 1;
        this.moverSubArvoreBaixo(newLeft, 2 * newLeft + 1);
        arv[newLeft] = temp;

        this.moverSubArvoreSobe(2 * dir + 1, 2 * newLeft + 2);
        this.moverSubArvoreSobe(2 * dir + 2, 2 * i + 2);

        this.Calc_fb(arv, newLeft);
        this.Calc_fb(arv, i);
    }

    private void rot_dir_esq(String[] arv, int i) {
        this.rot_dir(arv, 2 * i + 2);
        this.rot_esq(arv, i);
    }

    private void rot_esq_dir(String[] arv, int i) {
        this.rot_esq(arv, 2 * i + 1);
        this.rot_dir(arv, i);
    }

    private void isBalanced(String[] arv, int i) {
        if (i >= arv.length || arv[i] == null)
            return;
        this.isBalanced(arv, 2 * i + 1);
        this.isBalanced(arv, 2 * i + 2);

        if (this.Fb[i] > 1) {
            if (this.Fb[2 * i + 1] >= 0)
                this.rot_dir(arv, i);
            else
                this.rot_esq_dir(arv, i);
        } else if (this.Fb[i] < -1) {
            if (this.Fb[2 * i + 2] <= 0)
                this.rot_esq(arv, i);
            else
                this.rot_dir_esq(arv, i);
        }
    }

    @Override
    public boolean insert(String value) {
        if (value != null) {
            boolean res = super.insert(value);
            String[] arv = this.getHeap();
            int i = this.looker(value, arv);

            if (i > -1) {
                int pai = (i % 2 == 0) ? (i - 2) / 2 : (i - 1) / 2;
                this.Calc_fb(arv, pai);

                this.isBalanced(arv, 0);
            }
            return res;
        }
        return false;
    }

    private int looker(String aux, String[] heap) {
        if (aux != null) {
            int i = 0;

            while (i < heap.length && heap[i] != null) {
                if (heap[i].equals(aux)) {
                    return i;
                }

                if (heap[i].compareTo(aux) < 0) {
                    i = 2 * i + 2;
                } else if (heap[i].compareTo(aux) > 0) {
                    i = 2 * i + 1;
                }
            }
        }

        return -1;
    }

    @Override
    public boolean remove(String value) {
        if (value != null) {
            String[] arv = this.getHeap();
            int i = this.looker(value, arv);
            boolean res = super.remove(value);

            if (res) {
                int pai = (i % 2 == 0) ? (i - 2) / 2 : (i - 1) / 2;
                this.Calc_fb(arv, pai);
                this.isBalanced(arv, 0);
            }
            return res;
        }

        return false;
    }
}