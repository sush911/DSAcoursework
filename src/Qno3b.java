/*Imagine you are managing a bus service with passengers boarding at various stops along the route. Your task is to
optimize the boarding process by rearranging the order of passengers at intervals of k stops, where k is a positive
integer. If the total number of passengers is not a multiple of k, then the remaining passengers at the end of the route
should maintain their original order*/


public class Qno3b {

    // Definition for singly-linked list.
    static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // Function to reverse nodes in k-group
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode groupPrev = dummy;

        while (true) {
            ListNode kthNode = groupPrev;
            int count = 0;

            // Find the kth node
            while (count < k && kthNode != null) {
                kthNode = kthNode.next;
                count++;
            }

            if (kthNode == null) break;

            ListNode groupNext = kthNode.next;
            // Reverse the current group
            ListNode prev = groupPrev.next;
            ListNode curr = prev;
            ListNode nextNode = null;

            while (curr != groupNext) {
                nextNode = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextNode;
            }

            // Connect the reversed group with the previous part of the list
            groupPrev.next.next = groupNext;
            ListNode newGroupPrev = groupPrev.next;
            groupPrev.next = prev;

            groupPrev = newGroupPrev;
        }

        return dummy.next;
    }

    // Helper function to print linked list
    public static void printLinkedList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + (head.next != null ? " -> " : ""));
            head = head.next;
        }
        System.out.println();
    }

    // Test example
    public static void main(String[] args) {
        // Create the linked list: 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode(5)))));

        int k = 2;
        ListNode newHead = reverseKGroup(head, k);

        // Print the resulting linked list
        printLinkedList(newHead); // Expected output: 2 -> 1 -> 4 -> 3 -> 5
    }
}
