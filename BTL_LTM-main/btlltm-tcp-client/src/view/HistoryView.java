package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;
import javax.swing.table.DefaultTableCellRenderer;

public class HistoryView extends JFrame {
    private JTable tblHistory;
    private JButton btnClose;

    public HistoryView() {
        initComponents();
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/icon.png"));
        setIconImage(icon.getImage());
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lịch sử đấu");

        tblHistory = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblHistory);    
        
        btnClose = new JButton("OK");
        btnClose.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(btnClose, BorderLayout.SOUTH);

        setSize(640, 300);
        setLocationRelativeTo(null);
    }

    private void customizeComponents() {
        // Add custom styling here, similar to HomeView
    }

    public void setHistoryData(Vector<Vector<Object>> data, Vector<String> columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblHistory.setModel(model);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tblHistory.getColumnCount(); i++) {
            tblHistory.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        tblHistory.getColumnModel().getColumn(0).setPreferredWidth(160);
        tblHistory.getColumnModel().getColumn(1).setPreferredWidth(160);
        tblHistory.getColumnModel().getColumn(7).setPreferredWidth(120);
    }

}
