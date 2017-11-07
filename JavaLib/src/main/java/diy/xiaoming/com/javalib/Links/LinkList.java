package diy.xiaoming.com.javalib.Links;

/**
 * Created by Administrator on 2017-11-06.
 */

public class LinkList {
    private Link first;


    public LinkList() {
        first = null;
    }

    public boolean isEmpty() {
        return (first == null);
    }


    public void insertFirst(String name, int leg) {
        Link newLink = new Link(name, leg);
        newLink.next = first;
        first = newLink;
    }

    /**
     * call this method before should call isEmpty() first
     *
     * @return the first node
     */
    public Link deleteFirst() {
        Link temp = first;
        first = first.next;
        return temp;
    }

    /**
     * 链表的尾端最后一个链结点，它的next字段为null
     */
    public void displayList() {
        System.out.println("List (first-->last:)");
        Link current = first;
        while (current != null) {
            current.displayLink();
            current = current.next;
        }
    }

    /**
     * 根据关键字查找链表
     *
     * @param key
     * @return
     */
    public Link find(String key) {
        Link current = first;
        while (current.name != key) {
            if (current.next == null) {
                return null;
            } else {
                current = current.next;
            }
        }
        return current;
    }

    /**
     * 根据关键字删除链表
     * @param key
     * @return
     */
    public Link delete(String key){
        Link current = first;
        Link previous = first;
        while (current.name != key) {
            if (current.next == null) {
                return null;
            }else{
                previous = current;
                current = current.next;
            }
        }
        if (current==first) {
            first = first.next;
        }else{
            previous.next = current.next;
        }
//        while (current.next!=null){
//            if(current.name.equals(key)){
//                current.next = current.next.next;
//                return null;
//            }
//            current = current.next;
//        }
        return current;
    }
}
