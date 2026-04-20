package mlbb;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TreeMLBBTutorial {

    static class ItemNode {
        String name;
        List<ItemNode> children;

        ItemNode(String name) {
            this.name = name;
            this.children = new ArrayList<>();
        }

        void addChild(ItemNode child) {
            children.add(child);
        }
    }


    static void printAllBuildPaths(ItemNode node, List<String> path) {
        if (node == null) return;
        path.add(node.name);
        if (node.children.isEmpty()) {
            System.out.println(String.join(" -> ", path));
        } else {
            for (ItemNode child : node.children) printAllBuildPaths(child, path);
        }
        path.remove(path.size() - 1);
    }

    static int countNodes(ItemNode node) {
        if (node == null) return 0;
        int total = 1;
        for (ItemNode child : node.children) total += countNodes(child);
        return total;
    }

    static int countLeaves(ItemNode node) {
        if (node == null) return 0;
        if (node.children.isEmpty()) return 1;
        int total = 0;
        for (ItemNode child : node.children) total += countLeaves(child);
        return total;
    }

    static int height(ItemNode node) {
        if (node == null) return 0;
        if (node.children.isEmpty()) return 1;
        int maxChildHeight = 0;
        for (ItemNode child : node.children)
            maxChildHeight = Math.max(maxChildHeight, height(child));
        return 1 + maxChildHeight;
    }

    static boolean findPath(ItemNode node, String target, List<String> path) {
        if (node == null) return false;
        path.add(node.name);
        if (node.name.equalsIgnoreCase(target)) return true;
        for (ItemNode child : node.children) {
            if (findPath(child, target, path)) return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    static ItemNode findNode(ItemNode node, String target) {
        if (node == null) return null;
        if (node.name.equalsIgnoreCase(target)) return node;
        for (ItemNode child : node.children) {
            ItemNode result = findNode(child, target);
            if (result != null) return result;
        }
        return null;
    }

    public static void main(String[] args) {

        // ════════════════════════════════════════
        // BUILD TREE
        // ════════════════════════════════════════
        ItemNode root = new ItemNode("Start Build");

        ItemNode fleetingtime = new ItemNode("Fleeting time");
        fleetingtime.addChild(new ItemNode("Hero's Ring"));
        fleetingtime.addChild(new ItemNode("Hero's Ring"));
        fleetingtime.addChild(new ItemNode("Expert Gloves"));
        // Guardian Helmet: Ares Belt x2 + Dreadnaught Armor (Leather Jerkin x2)
        ItemNode guardianHelmet = new ItemNode("Guardian Helmet");
        guardianHelmet.addChild(new ItemNode("Ares Belt"));
        guardianHelmet.addChild(new ItemNode("Ares Belt"));
        guardianHelmet.addChild(new ItemNode("Ares Belt"));
        
        ItemNode chasitepauldron = new ItemNode("Chasite Pauldron");
        ItemNode dread2 = new ItemNode("Steel Legpants");
        dread2.addChild(new ItemNode("Leather Jerkin"));
        chasitepauldron.addChild(dread2);
        ItemNode dread3 = new ItemNode("Ares belt");
        dread3.addChild(new ItemNode("Vitality Crystal"));
        chasitepauldron.addChild(dread3);

        // Immortality: Ares Belt + Leather Jerkin + Vitality Crystal
        ItemNode immortality = new ItemNode("Immortality");
        ItemNode dread1 = new ItemNode("Ares belt");
        dread1.addChild(new ItemNode("Vitality crystal"));
        immortality.addChild(dread1);
        immortality.addChild(new ItemNode("Leather Jerkin"));
        immortality.addChild(new ItemNode("Vitality Crystal"));

        // Dominance Ice: Ares Belt + Magic Resist Cloak + Steel Legplates
        ItemNode dominanceIce = new ItemNode("Dominance Ice");
        ItemNode dread4 = new ItemNode("Ares Belt");
        dread4.addChild(new ItemNode("Vitality Crystal"));
        dominanceIce.addChild(dread4);
        dominanceIce.addChild(new ItemNode("Magic Resist Cloak"));
        dominanceIce.addChild(new ItemNode("Steel Legplates"));

        // Cursed Helmet: Vitality Crystal + Molten Essence (Vitality Crystal) + Magic Resist Cloak
        ItemNode cursedHelmet = new ItemNode("Cursed Helmet");
        cursedHelmet.addChild(new ItemNode("Vitality Crystal"));
        ItemNode molten1 = new ItemNode("Molten Essence");
        molten1.addChild(new ItemNode("Vitality Crystal"));
        cursedHelmet.addChild(molten1);
        cursedHelmet.addChild(new ItemNode("Magic Resist Cloak"));

        // Thunder Belt: Ares Belt + Leather Jerkin + Magic Resist Cloak
        ItemNode thunderBelt = new ItemNode("Thunder Belt");
        thunderBelt.addChild(new ItemNode("Ares Belt"));
        thunderBelt.addChild(new ItemNode("Leather Jerkin"));
        thunderBelt.addChild(new ItemNode("Magic Resist Cloak"));

        // Athena's Shield: Molten Essence (Vitality Crystal) + Vitality Crystal + Magic Resist Cloak
        ItemNode athenasShield = new ItemNode("Athena's Shield");
        ItemNode molten2 = new ItemNode("Molten Essence");
        molten2.addChild(new ItemNode("Vitality Crystal"));
        athenasShield.addChild(molten2);
        athenasShield.addChild(new ItemNode("Vitality Crystal"));
        athenasShield.addChild(new ItemNode("Magic Resist Cloak"));

        // Antique Cuirass: Vitality Crystal + Dreadnaught Armor (Leather Jerkin x2) + Steel Legplates
        ItemNode antiqueCuirass = new ItemNode("Antique Cuirass");
        antiqueCuirass.addChild(new ItemNode("Vitality Crystal"));
        ItemNode dread2 = new ItemNode("Dreadnaught Armor");
        dread2.addChild(new ItemNode("Leather Jerkin"));
        dread2.addChild(new ItemNode("Leather Jerkin"));
        antiqueCuirass.addChild(dread2);
        antiqueCuirass.addChild(new ItemNode("Steel Legplates"));

        // Oracle: Ares Belt + Healing Necklace + Magic Resist Cloak
        ItemNode oracle = new ItemNode("Oracle");
        oracle.addChild(new ItemNode("Ares Belt"));
        oracle.addChild(new ItemNode("Healing Necklace"));
        oracle.addChild(new ItemNode("Magic Resist Cloak"));
        
        root.addChild(fleetingtime);
        root.addChild(guardianHelmet);
        root.addChild(chasitepauldron);
        root.addChild(immortality);
        root.addChild(dominanceIce);
        root.addChild(cursedHelmet);
        root.addChild(thunderBelt);
        root.addChild(athenasShield);
        root.addChild(antiqueCuirass);
        root.addChild(oracle);

        // ════════════════════════════════════════
        // OUTPUT TREE
        // ════════════════════════════════════════
        System.out.println("=== STRUKTUR POHON ITEM TANK MLBB ===");
        System.out.println();
        printTree(root, 0);

        System.out.println();
        System.out.println("=== SEMUA JALUR BUILD ===");
        System.out.println();
        printAllBuildPaths(root, new ArrayList<>());

        System.out.println();
        System.out.println("=== STATISTIK POHON ===");
        System.out.println("Total Node   : " + countNodes(root));
        System.out.println("Total Daun   : " + countLeaves(root));
        System.out.println("Tinggi Pohon : " + height(root));

        // ════════════════════════════════════════
        // SCANNER — HISTORY BUILD (maks 6 item)
        // ════════════════════════════════════════
        System.out.println();
        System.out.println("===========================================");
        System.out.println("       MLBB TANK BUILD ADVISOR             ");
        System.out.println("===========================================");
        System.out.println();
        System.out.println("[Item Jadi]");
        for (ItemNode item : root.children) System.out.println("  - " + item.name);

        System.out.println("[Bahan]");
        List<String> bahanSudahTampil = new ArrayList<>();
        for (ItemNode item : root.children) {
            for (ItemNode bahan : item.children) {
                if (!bahanSudahTampil.contains(bahan.name)) {
                    bahanSudahTampil.add(bahan.name);
                    System.out.println("  - " + bahan.name);
                }
                for (ItemNode sub : bahan.children) {
                    if (!bahanSudahTampil.contains(sub.name)) {
                        bahanSudahTampil.add(sub.name);
                        System.out.println("    - " + sub.name);
                    }
                }
            }
        }

        System.out.println();
        System.out.println("Perintah: 'history' | 'clear' | 'exit'");
        System.out.println("Maksimal item dalam build: 6 slot");

        // ArrayList history build dengan batas 6
        ArrayList<String> historyBuild = new ArrayList<>(6);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.print(">> Masukkan item: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println();
                System.out.println("=== HISTORY BUILD FINAL ===");
                if (historyBuild.isEmpty()) {
                    System.out.println("(Tidak ada item yang dibeli)");
                } else {
                    List<String> fullPath = new ArrayList<>();
                    fullPath.add("Start Build");
                    fullPath.addAll(historyBuild);
                    System.out.println(String.join(" -> ", fullPath));
                    System.out.println("Slot terpakai: " + historyBuild.size() + "/6");
                }
                System.out.println("GG WP!");
                break;

            } else if (input.equalsIgnoreCase("history")) {
                System.out.println();
                System.out.println("=== HISTORY BUILD ===");
                if (historyBuild.isEmpty()) {
                    System.out.println("(Belum ada item yang dibeli)");
                } else {
                    List<String> fullPath = new ArrayList<>();
                    fullPath.add("Start Build");
                    fullPath.addAll(historyBuild);
                    System.out.println(String.join(" -> ", fullPath));
                    System.out.println("Slot terpakai: " + historyBuild.size() + "/6");
                }

            } else if (input.equalsIgnoreCase("clear")) {
                historyBuild.clear();
                System.out.println("History build berhasil dihapus!");

            } else {
                // Cek batas 6 slot
                if (historyBuild.size() >= 6) {
                    System.out.println("Build sudah penuh! Maksimal 6 item. Ketik 'clear' untuk reset.");
                    continue;
                }

                ItemNode found = findNode(root, input);
                if (found != null && !found.name.equals("Start Build")) {
                    historyBuild.add(found.name);
                    System.out.println("\"" + found.name + "\" ditambahkan! (Slot " + historyBuild.size() + "/6)");

                    if (!found.children.isEmpty()) {
                        System.out.println("Bahan:");
                        for (ItemNode bahan : found.children) {
                            System.out.print("  - " + bahan.name);
                            if (!bahan.children.isEmpty()) {
                                List<String> subNames = new ArrayList<>();
                                for (ItemNode sub : bahan.children) subNames.add(sub.name);
                                System.out.print(" (dari: " + String.join(", ", subNames) + ")");
                            }
                            System.out.println();
                        }
                    }
                } else {
                    System.out.println("Item \"" + input + "\" tidak ditemukan.");
                }
            }
        }

        scanner.close();
    }

    private static void printTree(ItemNode root, int i) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'printTree'");
    }
}