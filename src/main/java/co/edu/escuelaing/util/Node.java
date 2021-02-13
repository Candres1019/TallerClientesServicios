package co.edu.escuelaing.util;

/**
 * Clase Node, utilizada en la lista enlazada.
 */
public class Node {
    private Double datos;
    public Node next;

    /**
     * Metodo constructor del nodo.
     * @param datos - datos con los que inicia el nodo.
     */
    public Node(Double datos) {
        this.datos = datos;
        next = null;
    }

    /**
     * Metodo para solicitar los datos del nodo.
     * @return -
     */
    public Double getDatos() {
        return datos;
    }

    /**
     * Poner el valor asociado a los datos.
     * @param datos - datos a ser guardados.
     */
    public void setDatos(Double datos) {
        this.datos = datos;
    }

    /**
     * Solicitar el nodo siguiente
     * @return -
     */
    public Node getNext() {
        return next;
    }

    /**
     * Cambia el nodo next por el nodo dado.
     * @param next - nodo a ser puesto como next.
     */
    public void setNext(Node next) {
        this.next = next;
    }
}