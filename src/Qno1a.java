import java.util.*;


public class Qno1a {

    public static int mostUsedClassroom(int n, int[][] classes) {
        // Sort classes by start time, and by size (end-start) if start times are the same
        Arrays.sort(classes, (a, b) -> {
            if (a[0] != b[0]) return a[0] - b[0];
            return (b[1] - b[0]) - (a[1] - a[0]); // prioritize larger classes if start times are the same
        });

        // Priority queue (min-heap) to track room availability
        PriorityQueue<int[]> roomsHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));

        // Initialize counts for each room
        int[] roomCounts = new int[n];

        for (int[] currentClass : classes) {
            int start = currentClass[0];
            int end = currentClass[1];

            // Release rooms that are available before the start time of the current class
            while (!roomsHeap.isEmpty() && roomsHeap.peek()[0] <= start) {
                roomsHeap.poll();
            }

            int roomId;
            if (roomsHeap.size() < n) {
                // Assign the class to a new room if available
                roomId = roomsHeap.size();
                roomsHeap.offer(new int[]{end, roomId});
            } else {
                // Delay the class to the earliest available time
                int[] earliestAvailableRoom = roomsHeap.poll();
                roomId = earliestAvailableRoom[1];
                roomsHeap.offer(new int[]{earliestAvailableRoom[0] + (end - start), roomId});
            }

            // Increment the count of classes for the used room
            roomCounts[roomId]++;
        }

        // Find the room with the most classes
        int maxClasses = Arrays.stream(roomCounts).max().getAsInt();
        for (int i = 0; i < n; i++) {
            if (roomCounts[i] == maxClasses) {
                return i;
            }
        }

        return -1; // Should never reach here if n > 0
    }

    public static void main(String[] args) {
        int n = 2;
        int[][] classes = {
                {0, 10}, {1, 5}, {2, 7}, {3, 4}
        };

        System.out.println(mostUsedClassroom(n, classes));  // Output: 0
    }
}
