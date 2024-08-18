/*Implement travelling a salesman problem using hill climbing algorithm*/

import java.util.Arrays;
import java.util.Random;

public class Qno5a {
    private static final int NUM_CITIES = 5; // Number of cities
    private static final int MAX_ITERATIONS = 1000; // Maximum iterations for the hill climbing

    // Distances between each pair of cities
    private static final int[][] DISTANCES = {
            {0, 10, 15, 20, 25},
            {10, 0, 35, 25, 30},
            {15, 35, 0, 30, 20},
            {20, 25, 30, 0, 15},
            {25, 30, 20, 15, 0}
    };

    public static void main(String[] args) {
        // Initialize a random tour
        int[] tour = initializeTour(NUM_CITIES);
        System.out.println("Initial tour: " + Arrays.toString(tour));
        System.out.println("Initial tour length: " + calculateTourLength(tour));

        // Hill climbing algorithm
        tour = hillClimbing(tour);

        System.out.println("Optimized tour: " + Arrays.toString(tour));
        System.out.println("Optimized tour length: " + calculateTourLength(tour));
    }

    // Initialize a random tour
    private static int[] initializeTour(int numCities) {
        int[] tour = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            tour[i] = i;
        }
        shuffleArray(tour);
        return tour;
    }

    // Shuffle array to create a random tour
    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    // Calculate the length of the tour
    private static int calculateTourLength(int[] tour) {
        int length = 0;
        for (int i = 0; i < tour.length - 1; i++) {
            length += DISTANCES[tour[i]][tour[i + 1]];
        }
        length += DISTANCES[tour[tour.length - 1]][tour[0]]; // Return to starting city
        return length;
    }

    // Hill climbing algorithm to optimize the tour
    private static int[] hillClimbing(int[] tour) {
        int[] bestTour = tour.clone();
        int bestLength = calculateTourLength(bestTour);

        for (int i = 0; i < MAX_ITERATIONS; i++) {
            int[] newTour = twoOptSwap(bestTour);
            int newLength = calculateTourLength(newTour);

            if (newLength < bestLength) {
                bestTour = newTour;
                bestLength = newLength;
            }
        }

        return bestTour;
    }

    // Perform a 2-opt swap to generate a new tour
    private static int[] twoOptSwap(int[] tour) {
        int[] newTour = tour.clone();
        Random rand = new Random();
        int i = rand.nextInt(tour.length);
        int j = rand.nextInt(tour.length);

        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
        }

        // Reverse the segment between i and j
        while (i < j) {
            int temp = newTour[i];
            newTour[i] = newTour[j];
            newTour[j] = temp;
            i++;
            j--;
        }

        return newTour;
    }
}
