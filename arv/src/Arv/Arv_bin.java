package Arv;

public class Arv_bin {
    private String[] heap;
    int len;

    Arv_bin(int tamanho) {
        this.heap = new String[tamanho];
        this.len = 0;
    }

    public boolean insert(String value) {
        if (value != null) {
            int i = 0;

            while (i < this.heap.length) {
                if (this.heap[i] == null) {
                    this.heap[i] = value;
                    this.len++;
                    return true;
                }

                if (this.heap[i].compareTo(value) < 0) {
                    i = 2 * i + 2;
                } else if (this.heap[i].compareTo(value) > 0) {
                    i = 2 * i + 1;
                } else
                    return false;
            }
        }
        return false;
    }

    public int len() {
        return this.len;
    }

    protected void setLen(int i) {
        this.len = i;
    }

    protected void setHeap(int i, String value) {
        this.heap[i] = value;
    }

    protected String getNo(int i) {
        return this.heap[i];
    }

    protected String[] getHeap() {
        return this.heap;
    }

    public boolean find(String aux) {
        if (aux != null) {
            int i = 0;

            while (i < this.heap.length && this.heap[i] != null) {
                if (this.heap[i].equals(aux)) {
                    return true;
                }

                if (this.heap[i].compareTo(aux) < 0) {
                    i = 2 * i + 2;
                } else if (this.heap[i].compareTo(aux) > 0) {
                    i = 2 * i + 1;
                }
            }
        } else
            System.out.println("'aux' não existe.");

        return false;
    }

    protected void moverSubArvoreBaixo(int de, int para) {
        if (de >= this.heap.length || this.heap[de] == null)
            return;

        this.moverSubArvoreBaixo(2 * de + 1, 2 * para + 1);
        this.moverSubArvoreBaixo(2 * de + 2, 2 * para + 2);

        if (para < this.heap.length) {
            this.heap[para] = this.heap[de];
            this.heap[de] = null;
        }
    }

    protected void moverSubArvoreSobe(int de, int para) {
        if (de >= this.heap.length || this.heap[de] == null)
            return;

        if (para < this.heap.length) {
            this.heap[para] = this.heap[de];
            this.heap[de] = null;
        }
        this.moverSubArvoreSobe(2 * de + 1, 2 * para + 1);
        this.moverSubArvoreSobe(2 * de + 2, 2 * para + 2);
    }

    private void troca_max_esq(int raiz) {
        int maxEsq = 2 * raiz + 1;

        while (2 * maxEsq + 2 < this.heap.length && this.heap[2 * maxEsq + 2] != null) {
            maxEsq = 2 * maxEsq + 2;
        }

        if (maxEsq < heap.length) {
            this.heap[raiz] = this.heap[maxEsq];
            this.heap[maxEsq] = null;

            this.moverSubArvoreSobe(2 * maxEsq + 1, maxEsq);
        }
    }

    private void troca_min_dir(int raiz) {
        int minDir = 2 * raiz + 2;

        while (2 * minDir + 1 < this.heap.length && this.heap[2 * minDir + 1] != null) {
            minDir = 2 * minDir + 1;
        }

        if (minDir < this.heap.length) {
            this.heap[raiz] = this.heap[minDir];
            this.heap[minDir] = null;

            this.moverSubArvoreSobe(2 * minDir + 2, minDir);
        }
    }

    public boolean remove(String aux) {
        if (aux == null) {
            System.out.println("'v' não existe.");
            return false;
        }

        int i = 0;
        while (i < heap.length && heap[i] != null) {
            if (heap[i].equals(aux)) {
                int left = 2 * i + 1;

                if (left >= this.heap.length || this.heap[left] == null) {
                    troca_min_dir(i);
                } else
                    troca_max_esq(i);

                this.len--;
                return true;
            }

            if (heap[i].compareTo(aux) < 0) {
                i = 2 * i + 2;
            } else {
                i = 2 * i + 1;
            }
        }
        return false;
    }

    @Override
    public String toString() {

        String s = "digraph {\n";

        if (this.len == 1) {
            s += "\"" + 0 + " " + this.heap[0] + "\"";
            s += " }\n";
        } else {
            for (int i = 0; i < this.heap.length; i++) {
                if (this.heap[i] != null) {
                    for (int j = 1; j <= 2; j++) {
                        if (2 * i + j < this.heap.length && this.heap[2 * i + j] != null) {
                            s += "\"" + i + " " + this.heap[i] + "\" ->\"" + (2 * i + j) + " " + this.heap[2 * i + j]
                                    + "\"\n";
                        }
                    }
                }
            }
            s += "}\n";
        }

        return s;
    }
}
