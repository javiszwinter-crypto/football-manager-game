package com.footballmanager.ui.panels;

import com.footballmanager.data.GameData;
import com.footballmanager.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Dashboard panel showing club overview and statistics
 */
public class DashboardPanel extends JPanel {
    private Club club;
    private GameData gameData;

    public DashboardPanel(Club club, GameData gameData) {
        this.club = club;
        this.gameData = gameData;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createClubInfoPanel(), BorderLayout.NORTH);
        add(createStatisticsPanel(), BorderLayout.CENTER);
    }

    private JPanel createClubInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Club Information"));

        panel.add(createInfoLabel("Club:", club.getName()));
        panel.add(createInfoLabel("League:", club.getLeague()));
        panel.add(createInfoLabel("Budget:", String.format("$%.0f", club.getBudget())));
        panel.add(createInfoLabel("Squad Overall:", String.valueOf(club.getSquadOverall())));

        return panel;
    }

    private JLabel createInfoLabel(String key, String value) {
        JLabel label = new JLabel(String.format("<html><b>%s</b> %s</html>", key, value));
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        return label;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Season Statistics"));

        String[] columns = {"Statistic", "Value"};
        Object[][] data = {
            {"Matches Played", club.getMatches()},
            {"Wins", club.getWins()},
            {"Draws", club.getDraws()},
            {"Losses", club.getLosses()},
            {"Goals For", club.getGoalsFor()},
            {"Goals Against", club.getGoalsAgainst()},
            {"Goal Difference", club.getGoalDifference()},
            {"Points", club.getPoints()},
            {"League Position", club.getLeaguePosition()},
            {"Weekly Wage Cost", String.format("$%.0f", club.getTotalWeeklyCost())}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        JTable table = new JTable(model);
        table.setEnabled(false);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}
