package com.footballmanager.ui.panels;

import com.footballmanager.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Squad management panel
 */
public class SquadPanel extends JPanel {
    private Club club;
    private DefaultTableModel tableModel;
    private JTable table;

    public SquadPanel(Club club) {
        this.club = club;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createSquadTable(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createSquadTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Squad"));

        String[] columns = {"Name", "Position", "Age", "Overall", "Wage", "Contract", "Form", "Fitness"};
        tableModel = new DefaultTableModel(columns, 0);

        updateSquadTable();

        table = new JTable(tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void updateSquadTable() {
        tableModel.setRowCount(0);

        List<Player> squad = club.getSquad();
        for (Player player : squad) {
            Object[] row = {
                player.getName(),
                player.getPosition(),
                player.getAge(),
                player.getOverallRating(),
                String.format("$%.0f", player.getWage()),
                player.getContractYears() + " years",
                player.getForm() + "/10",
                player.getFitness() + "%"
            };
            tableModel.addRow(row);
        }
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateSquadTable());
        panel.add(refreshButton);

        JButton detailsButton = new JButton("Player Details");
        detailsButton.addActionListener(e -> showPlayerDetails());
        panel.add(detailsButton);

        return panel;
    }

    private void showPlayerDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a player");
            return;
        }

        String playerName = (String) tableModel.getValueAt(selectedRow, 0);
        Player player = club.getPlayer(playerName);

        if (player != null) {
            String details = String.format(
                "Name: %s\nPosition: %s\nAge: %d\nOverall: %d\n\n" +
                "Attributes:\nPace: %d\nShooting: %d\nPassing: %d\nDribbling: %d\nDefense: %d\nPhysical: %d\n\n" +
                "Contract: %d years\nWage: $%.0f/week\nMarket Value: $%.0f",
                player.getName(), player.getPosition(), player.getAge(), player.getOverallRating(),
                player.getPace(), player.getShooting(), player.getPassing(), player.getDribbling(),
                player.getDefense(), player.getPhysical(),
                player.getContractYears(), player.getWage(), player.getMarketValue()
            );

            JOptionPane.showMessageDialog(this, details, "Player Details", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
