package co.edu.escuelaing.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Clase implementacion de la lista enlazada.
 */
public class LinkedListImp implements List {

    private Node head;
    private int listSize;

    /**
     * Constructor de la clase LinkedListImp
     */
    public LinkedListImp(){
        head=null;
    }

    /* Metodos Nuevos Para la LinkedList */

    /**
     * Inserta un nuevo nodo al inicio de la lista.
     * @param datos - Valor double a agregar
     */
    public void addFirst(Double datos){
        Node node = new Node(datos);
        node.setNext(head);
        head=node;
        listSize = listSize + 1;
    }

    /**
     * Inserta un nuevo nodo al final de la lista.
     * @param datos - Valor double a agregar
     */
    public void addLast(Double datos){
        listSize = listSize + 1;
        Node node = new Node(datos);
        if (head == null){
            head = node;
        }
        else{
            node.setNext(null);
            Node last = head;
            while (last.getNext() != null){
                last = last.getNext();
            }
            last.next = node;
        }
    }

    /**
     * Inserta un nuevo nodo en medio de la lista dado un nodo anterior.
     * @param previusN - Nodo anterior donde insertar
     * @param datos - Valor double a agregar
     */
    public void addMiddle(Node previusN, Double datos){
        try{
            Node node = new Node(datos);
            node.setNext(previusN.getNext());
            previusN.setNext(node);
            listSize = listSize + 1;
        }catch (Exception e){
            System.out.println("Error, al ingresar nuevo dato, revise de nuevo.");
        }
    }

    // Metodos Getter y Setter

    /**
     * Obtener el nodo principal de la lista enlazada.
     * @return - Cabeza de la lista
     */
    public Node getHead() {
        return head;
    }

    /**
     * Obtener la completa en una cadena de datos.
     * @return - Datos de la lista en un string.
     */
    public String linkedListToString(){
        StringBuilder listaStr = new StringBuilder();
        Node last = head;
        while (last.getNext() != null){
            listaStr.append(" ").append(last.getDatos().toString());
            last = last.getNext();
        }
        listaStr.append(" ").append(last.getDatos().toString());
        return listaStr.toString();
    }

    /* Metodos SobreEscritos de List */

    // Metodos Implementados

    /**
     * Consultar tamaño de la lista
     */
    @Override
    public int size() {
        return listSize;
    }

    /**
     * Inserta un nuevo nodo al final de la lista
     * @param o - Valor double a agregar
     */
    @Override
    public boolean add(Object o) {
        try{
            Double number = (Double) o;
            this.addLast(number);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error, al ingresar nuevo dato, revise de nuevo.");
            return false;
        }
    }

    /**
     * Inserta un nuevo nodo a la lista dada una posición
     * @param index - Posicion en la lista donde agregar el double
     * @param element - Valor double a agregar
     */
    @Override
    public void add(int index, Object element) {
        try {
            Double dato = (Double) element;
            //public void addMiddle(Node previusN, Double datos){
            int Cont = -1;
            Node prev = head;
            Node sig = prev.getNext();
            if (sig == null){
                addMiddle(prev, dato);
            }
            else {
                while (prev.getNext() != null) {
                    Cont = Cont + 1;
                    if (Cont == index) {
                        addMiddle(prev, dato);
                    } else {
                        prev = sig;
                        sig = sig.getNext();
                    }
                }
                addMiddle(prev, dato);
            }
        }catch (Exception e){
            System.out.println("Error al eliminar elemento.");
        }
    }

    /**
     * Ingresa los datos de un arreglo a la lista
     * @param c - Lista de valores Double a agregar.
     */
    @Override
    public boolean addAll(Collection c) {
        try {
            for (Object number : c) {
                Double parseNumber = (Double) number;
                this.addLast(parseNumber);
            }
            return true;
        }catch (Exception e){
            System.out.println("Error Ingresando los datos del arreglo.");
            return false;
        }
    }

    /**
     * Eliminar un nodo dada una posicion
     * @param index - Posicion del nodo a eliminar.
     */
    @Override
    public Object remove(int index) {
        // Si solo existe la cabeza y dan otro numero diferente a 0 no se puede eliminar
        if (head == null && index != 0){
            return false;
        }
        Node temp = head;
        // Si el index es 0, se elimina la cabeza
        if (index == 0) {
            head = temp.getNext();
            return true;
        }
        // Encontrar el dato para ser eliminado
        for (int i = 0; temp != null && i < index - 1; i++) {
            temp = temp.getNext();
            return false;
        }
        // Si no se encontró el dato
        if (temp == null || temp.next == null){
            return false;
        }
        // Eliminar el nodo
        Node next = temp.getNext().getNext();
        temp.setNext(next);
        return true;
    }

    /**
     * Elimina un nodo dado su valor representativo
     * @param o Valor representativo del nodo
     */
    @Override
    public boolean remove(Object o) {
        try {
            boolean found = false;
            int Cont = -1;
            Double dato = (Double) o;
            Node headCp = head;
            while (headCp.getNext() != null) {
                Cont = Cont + 1;
                if(dato == headCp.getDatos()){
                    this.remove(Cont);
                    found = true;
                    break;
                }else {
                    headCp = headCp.getNext();
                }
            }
            return found;
        }catch (Exception e){
            System.out.println("Error al eliminar elemento.");
            return false;
        }
    }

    /**
     * Remueve todos los elementos de la lista
     */
    @Override
    public void clear() {
        head = null;
        listSize = 0;
    }

    /**
     * Revisar si la lista es vacia o no
     */
    @Override
    public boolean isEmpty() {
        return head==null;
    }

    // Metodos No Implementados

    /**
     * Metodo No Implementado
     * @param index - No implementado
     * @param c - No implementado
     * @return - No implementado
     */
    @Override
    public boolean addAll(int index, Collection c) {
        return false;
    }

    /**
     * Metodo No Implementado
     * @param o - No implementado
     * @return - No implementado
     */
    @Override
    public boolean contains(Object o) {
        return false;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public Iterator iterator() {
        return null;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public Object get(int index) {
        return null;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public Object set(int index, Object element) {
        return null;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public int indexOf(Object o) {
        return 0;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public ListIterator listIterator() {
        return null;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public ListIterator listIterator(int index) {
        return null;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public boolean retainAll(Collection c) {
        return false;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public boolean removeAll(Collection c) {
        return false;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    /**
     * Metodo No Implementado
     */
    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }

}