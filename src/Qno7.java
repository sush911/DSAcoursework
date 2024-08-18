/*Route Optimization for Delivery Service (Java GUI)*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class Qno7 extends JFrame {

    private JTextArea deliveryListArea;
    private JComboBox<String> algorithmSelection;
    private JTextField vehicleCapacityField;
    private JTextField maxDistanceField;
    private JTextArea routeOutputArea;
    private JButton optimizeButton;

    public Qno7() {
        setTitle("Route Optimization for Delivery Service");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // GUI Components
        deliveryListArea = new JTextArea(10, 40);
        algorithmSelection = new JComboBox<>(new String[]{"Dijkstra's Algorithm"});
        vehicleCapacityField = new JTextField("10", 5);
        maxDistanceField = new JTextField("100", 5);
        routeOutputArea = new JTextArea(10, 40);
        optimizeButton = new JButton("Optimize Route");

        // Layout setup
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new GridLayout(5, 1));
        topPanel.add(new JLabel("Delivery Points (Address, Priority):"));
        topPanel.add(new JScrollPane(deliveryListArea));
        topPanel.add(new JLabel("Select Algorithm:"));
        topPanel.add(algorithmSelection);
        topPanel.add(new JLabel("Vehicle Capacity:"));
        topPanel.add(vehicleCapacityField);
        topPanel.add(new JLabel("Max Driving Distance:"));
        topPanel.add(maxDistanceField);
        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(optimizeButton, BorderLayout.NORTH);
        bottomPanel.add(new JScrollPane(routeOutputArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.CENTER);

        // Optimize button action
        optimizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                optimizeRoute();
            }
        });
    }

    // Method to optimize the route
    private void optimizeRoute() {
        String[] deliveries = deliveryListArea.getText().split("\n");
        int vehicleCapacity = Integer.parseInt(vehicleCapacityField.getText());
        int maxDistance = Integer.parseInt(maxDistanceField.getText());

        // Dummy data processing (Replace with actual graph algorithm like Dijkstra)
        routeOutputArea.setText("Optimizing route using " + algorithmSelection.getSelectedItem() + "...\n");

        List<String> deliveryPoints = Arrays.asList(deliveries);
        Collections.sort(deliveryPoints, (a, b) -> Integer.compare(extractPriority(b), extractPriority(a)));

        // Here you could implement and use a real graph algorithm
        routeOutputArea.append("Optimal Route:\n");
        int totalDistance = 0;
        for (String point : deliveryPoints) {
            routeOutputArea.append(point + "\n");
            totalDistance += 10; // Simulated distance
            if (totalDistance > maxDistance || deliveryPoints.size() > vehicleCapacity) {
                routeOutputArea.append("Reached vehicle or distance capacity limits!\n");
                break;
            }
        }

        routeOutputArea.append("Total Distance: " + totalDistance + " km\n");
    }

    // Helper method to extract priority from input
    private int extractPriority(String delivery) {
        try {
            return Integer.parseInt(delivery.split(",")[1].trim());
        } catch (Exception e) {
            return 0; // Default priority
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Qno7().setVisible(true);
            }
        });
    }
}
