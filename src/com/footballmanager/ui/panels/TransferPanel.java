package com.footballmanager.ui.panels;

import com.footballmanager.data.GameData;
import com.footballmanager.model.*;

import javax.swing.*;
import java.awt.*;

/**
 * Transfer market panel for buying and selling players
 */
public class TransferPanel extends JPanel {
    private Club playerClub;
    private GameData gameData;

    public TransferPanel(Club playerClub, GameData gameData) {
        this.playerClub = playerClub;
        this.gameData = gameData;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(createTransferPanel(), BorderLayout.CENTER);
    }

    private JPanel createTransferPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));

        panel.add(createBuyPlayersPanel());
        panel.add(createSellPlayersPanel());

        return panel;
    }

    private JPanel createBuyPlayersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Buy Players"));

        JLabel infoLabel = new JLabel(String.format("Available Budget: $%.0f", playerClub.getBudget()));
        panel.add(infoLabel, BorderLayout.NORTH);

        JButton buyButton = new JButton("Browse Available Players");
        buyButton.addActionListener(e -> showAvailablePlayersDialog());
        panel.add(buyButton, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSellPlayersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Sell Players"));

        JButton sellButton = new JButton("Sell Squad Players");
        sellButton.addActionListener(e -> showSellPlayersDialog());
        panel.add(sellButton, BorderLayout.CENTER);

        return panel;
    }

    private void showAvailablePlayersDialog() {
        java.util.List<Club> clubs = gameData.getClubs();
        java.util.List<Player> availablePlayers = new java.util.ArrayList<>();

        for (Club club : clubs) {
            if (club.getId() != playerClub.getId()) {
                for (Player player : club.getSquad()) {
                    if (player.isForSale()) {
                        availablePlayers.add(player);
                    }
                }
            }
        }

        if (availablePlayers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No players available for transfer");
            return;
        }

        Player[] players = availablePlayers.toArray(new Player[0]);
        Player selectedPlayer = (Player) JOptionPane.showInputDialog(
            this,
            "Select a player to buy:",
            "Transfer Market",
            JOptionPane.QUESTION_MESSAGE,
            null,
            players,
            players[0]
        );

        if (selectedPlayer != null) {
            attemptTransfer(selectedPlayer);
        }
    }

    private void attemptTransfer(Player player) {
        if (playerClub.getBudget() < player.getMarketValue()) {
            JOptionPane.showMessageDialog(this, "Insufficient budget to buy this player");
            return;
        }

        int response = JOptionPane.showConfirmDialog(
            this,
            String.format("Buy %s for $%.0f?", player.getName(), player.getMarketValue()),
            "Confirm Transfer",
            JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            playerClub.subtractBudget(player.getMarketValue());
            playerClub.addPlayer(player);
            JOptionPane.showMessageDialog(this, "Transfer completed successfully!");
        }
    }

    private void showSellPlayersDialog() {
        java.util.List<Player> squad = playerClub.getSquad();
        if (squad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No players to sell");
            return;
        }

        Player[] players = squad.toArray(new Player[0]);
        Player selectedPlayer = (Player) JOptionPane.showInputDialog(
            this,
            "Select a player to sell:",
            "Sell Player",
            JOptionPane.QUESTION_MESSAGE,
            null,
            players,
            players[0]
        );

        if (selectedPlayer != null) {
            attemptSale(selectedPlayer);
        }
    }

    private void attemptSale(Player player) {
        int response = JOptionPane.showConfirmDialog(
            this,
            String.format("Sell %s for $%.0f?", player.getName(), player.getMarketValue()),
            "Confirm Sale",
            JOptionPane.YES_NO_OPTION
        );

        if (response == JOptionPane.YES_OPTION) {
            playerClub.addBudget(player.getMarketValue());
            playerClub.removePlayer(player);
            JOptionPane.showMessageDialog(this, "Player sold successfully!");
        }
    }
}
