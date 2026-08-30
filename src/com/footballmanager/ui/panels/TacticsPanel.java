package com.footballmanager.ui.panels;

import com.footballmanager.model.Club;

import javax.swing.*;
import java.awt.*;

/**
 * Tactics and formation panel
 */
public class TacticsPanel extends JPanel {
    private Club club;
    private JLabel formationLabel;

    public TacticsPanel(Club club) {
        this.club = club;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createFormationPanel(), BorderLayout.CENTER);
        add(createInfoPanel(), BorderLayout.SOUTH);
    }

    private JPanel createFormationPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Choose Formation"));

        String[] formations = {"4-4-2", "4-3-3", "5-3-2", "3-5-2"};

        for (String formation : formations) {
            JButton button = new JButton(formation);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(e -> changeFormation(formation));
            panel.add(button);
        }

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Current Tactics"));

        formationLabel = new JLabel(String.format("Formation: %s", club.getFormation()));
        formationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(formationLabel);

        return panel;
    }

    private void changeFormation(String formation) {
        club.setFormation(formation);
        formationLabel.setText(String.format("Formation: %s", formation));
        JOptionPane.showMessageDialog(this, String.format("Formation changed to %s", formation));
    }
}
