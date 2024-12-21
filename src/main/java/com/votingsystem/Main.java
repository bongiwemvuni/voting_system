package com.votingsystem;

import com.votingsystem.model.Voter;
import com.votingsystem.model.Party;
import com.votingsystem.dao.VoterDAO;
import com.votingsystem.dao.PartyDAO;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static VoterDAO voterDAO = new VoterDAO();
    private static PartyDAO partyDAO = new PartyDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Voting System ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. View Results");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    viewResults();
                    break;
                case 4:
                    System.out.println("Thank you for using the Voting System!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void register() {
        System.out.println("\n=== Register ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Voter voter = new Voter();
        voter.setUsername(username);
        voter.setPassword(password);
        voter.setEmail(email);

        if (voterDAO.registerVoter(voter)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed! Username or email already exists.");
        }
    }

    private static void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            Voter voter = voterDAO.authenticateVoter(username, password);
            if (voter != null) {
                if (!voter.isHasVoted()) {
                    showVotingMenu(voter);
                } else {
                    System.out.println("You have already voted!");
                }
            } else {
                System.out.println("Invalid credentials!");
            }
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private static void showVotingMenu(Voter voter) {
        try {
            List<Party> parties = partyDAO.getAllParties();
            System.out.println("\n=== Available Parties ===");
            for (int i = 0; i < parties.size(); i++) {
                Party party = parties.get(i);
                System.out.println((i + 1) + ". " + party.getName());
            }

            System.out.print("Enter the number of the party you want to vote for: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice > 0 && choice <= parties.size()) {
                Party selectedParty = parties.get(choice - 1);
                partyDAO.incrementVoteCount(selectedParty.getId());
                voterDAO.markAsVoted(voter.getId());
                System.out.println("Vote cast successfully!");
            } else {
                System.out.println("Invalid party selection!");
            }
        } catch (Exception e) {
            System.out.println("Voting failed: " + e.getMessage());
        }
    }

    private static void viewResults() {
        try {
            List<Party> parties = partyDAO.getAllParties();
            System.out.println("\n=== Election Results ===");
            for (Party party : parties) {
                System.out.println(party.getName() + ": " + party.getVoteCount() + " votes");
            }
        } catch (Exception e) {
            System.out.println("Failed to retrieve results: " + e.getMessage());
        }
    }
}
