package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;
import javax.swing.table.DefaultTableCellRenderer;

public class LeaderboardView extends JFrame {
    private JTable tblLeaderboard;
    private JButton btnClose;

    public LeaderboardView() {
        initComponents();
        ImageIcon icon = new ImageIcon(getClass().getResource("/image/icon.png"));
        setIconImage(icon.getImage());
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Bảng xếp hạng");

        tblLeaderboard = new JTable();
        JScrollPane scrollPane = new JScrollPane(tblLeaderboard);

        btnClose = new JButton("OK");
        btnClose.addActionListener(e -> dispose());

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(btnClose, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    private void customizeComponents() {
        // Add custom styling here, similar to HomeView
    }

    public void setLeaderboardData(Vector<Vector<Object>> data, Vector<String> columnNames) {
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblLeaderboard.setModel(model);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tblLeaderboard.getColumnCount(); i++) {
            tblLeaderboard.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}
