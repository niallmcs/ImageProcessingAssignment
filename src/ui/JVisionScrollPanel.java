package ui;

import visionsystem.JVision;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class JVisionScrollPanel extends JVision {
    private static final long serialVersionUID = 1L;
    JPanel images = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel imageContainer = new JPanel();
    JPanel contentPane = new JPanel();
    JScrollPane scrollPane;

    java.util.List<JPanel> imagePanels = new LinkedList<>();

    public JVisionScrollPanel(int width, int height) {
        super();
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        images.setAlignmentX( Component.LEFT_ALIGNMENT );
        imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS));
        imagePanels.add(images);
        imageContainer.add(images);
        scrollPane = new JScrollPane(imageContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(width, height));
        this.setContentPane(contentPane);
        this.setSize(new Dimension(width, height));
        this.pack();
        this.setVisible(true);
    }

    @Override
    public void imdisp(Image image, String figname, int xorigin, int yorigin) {
        imdisp(image, figname, xorigin, yorigin, 300);
    }

    @Override
    public void imdisp(Image image, String figname, int xorigin, int yorigin, int size) {
        ImageIcon imagePanel = new ImageIcon(image);
        JLabel jp = new JLabel(figname, imagePanel, 0);
        jp.setVerticalTextPosition(1);
        jp.setHorizontalTextPosition(0);
        this.images.add(jp);
        Insets insets = this.images.getInsets();
        jp.setBounds(xorigin + insets.left, yorigin + insets.top, size, size);
        this.images.repaint();
        this.scrollPane.repaint();
        this.contentPane.repaint();
        this.setVisible(true);
    }

    public void imageDisplay(Image image, Image histogram, String figname,  int xorigin, int yorigin, int size) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(2,1));
        ImageIcon imageIcon = new ImageIcon(image);

        JLabel jp = new JLabel(figname, imageIcon, 0);

        jp.setVerticalTextPosition(1);
        jp.setHorizontalTextPosition(0);

        imagePanel.add(jp);

        if(histogram != null){
            ImageIcon histogramImageIcon = new ImageIcon(histogram);
            JLabel jp2 = new JLabel(figname + " histogram", histogramImageIcon, 0);
            jp2.setVerticalTextPosition(1);
            jp2.setHorizontalTextPosition(0);
            imagePanel.add(jp2);
        }

        JPanel currentImagePanel = imagePanels.get(imagePanels.size()-1);
        currentImagePanel.add(imagePanel);
        currentImagePanel.repaint();

        this.scrollPane.repaint();
        this.contentPane.repaint();
        this.setVisible(true);
    }

    public void insertNewLine() {
        JPanel newImagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        newImagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        imagePanels.add(newImagePanel);
        imageContainer.add(newImagePanel);

        this.imageContainer.repaint();
        this.scrollPane.repaint();
        this.contentPane.repaint();
    }
}
