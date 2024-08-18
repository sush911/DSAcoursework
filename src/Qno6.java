/*This scenario presents another sample Java GUI application using multithreading and an asynchronous framework
(SwingWorker) to demonstrate asynchronous progress updates and batch processing.*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Qno6 extends JFrame {

    private JProgressBar overallProgressBar;
    private JTextArea statusArea;
    private JButton startButton;
    private JButton cancelButton;
    private JFileChooser fileChooser;
    private File[] selectedFiles;
    private JComboBox<String> conversionOptions;
    private ConversionTask conversionTask;

    public Qno6() {
        setTitle("File Conversion Tool");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // GUI components
        overallProgressBar = new JProgressBar(0, 100);
        statusArea = new JTextArea(10, 50);
        statusArea.setEditable(false);
        JScrollPane statusScrollPane = new JScrollPane(statusArea);
        startButton = new JButton("Start Conversion");
        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);

        conversionOptions = new JComboBox<>(new String[]{"PDF to Docx", "Image Resize"});
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(conversionOptions, BorderLayout.NORTH);
        topPanel.add(fileChooser, BorderLayout.CENTER);
        topPanel.add(startButton, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(statusScrollPane, BorderLayout.CENTER);
        add(overallProgressBar, BorderLayout.SOUTH);
        add(cancelButton, BorderLayout.WEST);

        // Button actions
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedFiles = fileChooser.getSelectedFiles();
                if (selectedFiles.length > 0) {
                    startButton.setEnabled(false);
                    cancelButton.setEnabled(true);
                    conversionTask = new ConversionTask(selectedFiles, (String) conversionOptions.getSelectedItem());
                    conversionTask.addPropertyChangeListener(new ProgressListener());
                    conversionTask.execute();
                } else {
                    JOptionPane.showMessageDialog(Qno6.this, "Please select files to convert.", "No Files Selected", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conversionTask != null) {
                    conversionTask.cancel(true);
                }
            }
        });
    }

    private class ConversionTask extends SwingWorker<Void, String> {
        private File[] files;
        private String conversionType;

        public ConversionTask(File[] files, String conversionType) {
            this.files = files;
            this.conversionType = conversionType;
        }

        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 0; i < files.length; i++) {
                if (isCancelled()) {
                    publish("Conversion cancelled.");
                    break;
                }
                // Simulate file conversion by sleeping for 1 second per file
                Thread.sleep(1000);
                publish("Converted: " + files[i].getName() + " to " + conversionType);
                int progress = (i + 1) * 100 / files.length;
                setProgress(progress);
            }
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            for (String message : chunks) {
                statusArea.append(message + "\n");
            }
        }

        @Override
        protected void done() {
            try {
                get(); // This will throw any exceptions that were encountered during doInBackground()
                statusArea.append("All conversions completed.\n");
            } catch (InterruptedException e) {
                statusArea.append("Conversion was interrupted.\n");
            } catch (ExecutionException e) {
                statusArea.append("Error during conversion: " + e.getCause().getMessage() + "\n");
            } finally {
                startButton.setEnabled(true);
                cancelButton.setEnabled(false);
                setProgress(0);
            }
        }
    }

    private class ProgressListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("progress".equals(evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                overallProgressBar.setValue(progress);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Qno6().setVisible(true);
            }
        });
    }
}
