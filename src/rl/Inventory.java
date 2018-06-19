package rl;

public class Inventory {
    private Item[] items;


    public Inventory(int max){
        items = new Item[max];

    }  //Inventory


    public Item[] getItems(){
        return items;
    } //getItems

    //gets item at specific slot
    public Item get(int i){
        return items[i];
    } //get


    //add item to open slot
    public void add(Item item){
        for (int i = 0; i < items.length; i++){
            if (items[i] == null){
                items[i] = item;
                break;
            } //if
        }//for

    } //add

    //removes item
    public void remove(Item item){
        for (int i = 0; i < items.length; i++){
            if (items[i] == item){
                items[i] = null;
                return;
            }
        } //for
    } //remove

    //check to see if inventory is full
    public boolean isFull(){
        int size = 0;
        for (int i = 0; i < items.length; i++){
            if (items[i] != null){
                size++;
            }
        }
        return size == items.length;
    } //isFull



} //Inventory class
