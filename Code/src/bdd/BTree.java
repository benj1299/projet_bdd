package bdd;

public class BTree<Key extends Comparable<Key>, Value>  {
    // enfants maximum par nœud d'arbre B = M-1 (doit être pair et supérieur à 2)
    private static final int M = 4;

    private Node root;       // racine du B-tree
    private int height;      // hauteur du B-tree
    private int n;           // nombre de key-value dans B-tree

    private static final class Node {
        private int m;                             // nombre d'enfants
        private Entry[] children = new Entry[M];   // tableaux des enfants

        // create a node with k children
        private Node(int k) {
            m = k;
        }
    }

    // internal nodes : utiliser uniquement key et next
    // external nodes : utiliser uniquement key et value
    private static class Entry {
        private Comparable key;
        private final Object val;
        private Node next;     // helper pour itérer sur les entrées du tableau
        public Entry(Comparable key, Object val, Node next) {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }

    /**
     * Initialise un arbre B vide.
     */
    public BTree() {
        root = new Node(0);
    }
 
    /**
     * Renvoie vrai si cette table de symboles est vide.
     * @return {@code true} si cette table de symboles est vide; {@code false} autrement
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Renvoie le nombre de paires clé-valeur dans cette table de symboles.
     * @return le nombre de paires clé-valeur dans cette table de symboles
     */
    public int size() {
        return n;
    }

    /**
     * Renvoie la hauteur de cet arbre B (pour le débogage).
     *
     * @return la hauteur de cet arbre B
     */
    public int height() {
        return height;
    }


    /**
     * Renvoie la valeur associée à la clé donnée.
     *
     * @param  key the key
     * @return la valeur associée à la clé donnée si la clé est dans la table des symboles 
     * 			et {@code null} si la clé n'est pas dans la table des symboles
     * @throws IllegalArgumentException si {@code key} est {@code null}
     */
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        return search(root, key, height);
    }

    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (Value) children[j].val;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.m; j++) {
                if (j+1 == x.m || less(key, children[j+1].key))
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }


    /**
     * Insère la paire clé-valeur dans la table des symboles, écrasant l'ancienne valeur
     * avec la nouvelle valeur si la clé est déjà dans la table des symboles.
     * Si la valeur est {@code null}, cela supprime effectivement la clé de la table des symboles.
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("argument key to put() is null");
        Node u = insert(root, key, val, height); 
        n++;
        if (u == null) return;

        // need to split root
        Node t = new Node(2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }

    private Node insert(Node h, Key key, Value val, int ht) {
        int j;
        Entry t = new Entry(key, val, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key)) break;
            }
        }

        // internal node
        else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht-1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--)
            h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        if (h.m < M) return null;
        else         return split(h);
    }

    // noeud divisé en deux
    private Node split(Node h) {
        Node t = new Node(M/2);
        h.m = M/2;
        for (int j = 0; j < M/2; j++)
            t.children[j] = h.children[M/2+j]; 
        return t;    
    }

    /**
     * Renvoie une représentation sous forme de chaîne de cet arbre B (pour le débogage).
     *
     * @return une représentation sous forme de chaîne de cet arbre B.
     */
    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(indent + children[j].key + " " + children[j].val + "\n");
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s.append(indent + "(" + children[j].key + ")\n");
                s.append(toString(children[j].next, ht-1, indent + "     "));
            }
        }
        return s.toString();
    }


    // fonctions de comparaison - rendre Comparable au lieu de Key pour éviter les casts
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
}