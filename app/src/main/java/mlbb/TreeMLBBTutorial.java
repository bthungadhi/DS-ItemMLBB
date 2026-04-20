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

    // PART B — Print Tree
    static void printTree(ItemNode node, int level) {
        if (node == null) return;
        String indent = "  ".repeat(level);
        System.out.println(indent + "- " + node.name);
        for (ItemNode child : node.children) printTree(child, level + 1);
    }

    // PART C — Print All Build Paths
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

    // PART D — Tree Operations
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

    // PART E — Find Path & Find Node
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

    // ════════════════════════════════════════════════════
    // TASK 2 — countItemOccurrences()
    // Menghitung berapa kali sebuah item muncul di seluruh pohon
    // Berguna karena satu bahan (misal Vitality Crystal) bisa dipakai banyak item
    // ════════════════════════════════════════════════════
    static int countItemOccurrences(ItemNode node, String target) {
        if (node == null) return 0; // Node kosong, tidak ada yang dihitung

        // Kalau nama node ini cocok dengan target, hitung 1
        int count = node.name.equalsIgnoreCase(target) ? 1 : 0;

        // Tambahkan hasil hitungan dari semua anak (rekursi)
        for (ItemNode child : node.children) {
            count += countItemOccurrences(child, target);
        }
        return count;
    }

    // ════════════════════════════════════════════════════
    // TASK 3 — printPathsEndingWith()
    // Mencetak hanya jalur yang berakhir dengan item tertentu (misal "Immortality")
    // ════════════════════════════════════════════════════
    // TASK 3 — printPathsEndingWith()
    static void printPathsEndingWith(ItemNode node, String target, List<String> path) {
        if (node == null) return;

        path.add(node.name); // Catat node saat ini ke jalur

        // Cek apakah nama node saat ini cocok dengan target
        if (node.name.equalsIgnoreCase(target)) {
            // Kalau cocok, cetak jalur dari root sampai node ini beserta semua sub-bahannya
            printAllBuildPaths(node, new java.util.ArrayList<>(path.subList(0, path.size() - 1)));
        } else {
            // Belum ketemu, terus ke anak-anak (rekursi)
            for (ItemNode child : node.children) {
                printPathsEndingWith(child, target, path);
            }
        }

        path.remove(path.size() - 1); // Backtracking
    }

    public static void main(String[] args) {

        // ════════════════════════════════════════
        // BUILD TREE
        // ════════════════════════════════════════
        ItemNode root = new ItemNode("Start Build");

        // TASK 1 — Branch baru: Fleeting Time (item non-tank sebagai cabang berbeda)
        // Bahan: Hero's Ring x2 + Expert Gloves
        ItemNode fleetingtime = new ItemNode("Fleeting Time");
        fleetingtime.addChild(new ItemNode("Hero's Ring"));
        fleetingtime.addChild(new ItemNode("Hero's Ring"));
        fleetingtime.addChild(new ItemNode("Expert Gloves"));

        // Guardian Helmet — Bahan: Ares Belt x3
        ItemNode guardianHelmet = new ItemNode("Guardian Helmet");
        guardianHelmet.addChild(new ItemNode("Ares Belt"));
        guardianHelmet.addChild(new ItemNode("Ares Belt"));
        guardianHelmet.addChild(new ItemNode("Ares Belt"));

        // Chastise Pauldron — Bahan: Steel Legpants + Ares Belt
        ItemNode chasitepauldron = new ItemNode("Chastise Pauldron");
        ItemNode steelLegpants = new ItemNode("Steel Legpants");
        steelLegpants.addChild(new ItemNode("Leather Jerkin"));
        chasitepauldron.addChild(steelLegpants);
        ItemNode aresBeltCP = new ItemNode("Ares Belt");
        aresBeltCP.addChild(new ItemNode("Vitality Crystal"));
        chasitepauldron.addChild(aresBeltCP);

        // Immortality — Bahan: Ares Belt (dari Vitality Crystal) + Leather Jerkin + Vitality Crystal
        ItemNode immortality = new ItemNode("Immortality");
        ItemNode aresBeltIM = new ItemNode("Ares Belt");
        aresBeltIM.addChild(new ItemNode("Vitality Crystal"));
        immortality.addChild(aresBeltIM);
        immortality.addChild(new ItemNode("Leather Jerkin"));
        immortality.addChild(new ItemNode("Vitality Crystal"));

        // Dominance Ice — Bahan: Ares Belt (dari Vitality Crystal) + Magic Resist Cloak + Steel Legplates
        ItemNode dominanceIce = new ItemNode("Dominance Ice");
        ItemNode aresBeltDI = new ItemNode("Ares Belt");
        aresBeltDI.addChild(new ItemNode("Vitality Crystal"));
        dominanceIce.addChild(aresBeltDI);
        dominanceIce.addChild(new ItemNode("Magic Resist Cloak"));
        dominanceIce.addChild(new ItemNode("Steel Legplates"));

        // Cursed Helmet — Bahan: Vitality Crystal + Molten Essence + Magic Resist Cloak
        ItemNode cursedHelmet = new ItemNode("Cursed Helmet");
        cursedHelmet.addChild(new ItemNode("Vitality Crystal"));
        ItemNode molten1 = new ItemNode("Molten Essence");
        molten1.addChild(new ItemNode("Vitality Crystal"));
        cursedHelmet.addChild(molten1);
        cursedHelmet.addChild(new ItemNode("Magic Resist Cloak"));

        // Thunder Belt — Bahan: Ares Belt + Leather Jerkin + Magic Resist Cloak
        ItemNode thunderBelt = new ItemNode("Thunder Belt");
        thunderBelt.addChild(new ItemNode("Ares Belt"));
        thunderBelt.addChild(new ItemNode("Leather Jerkin"));
        thunderBelt.addChild(new ItemNode("Magic Resist Cloak"));

        // Athena's Shield — Bahan: Molten Essence + Vitality Crystal + Magic Resist Cloak
        ItemNode athenasShield = new ItemNode("Athena's Shield");
        ItemNode molten2 = new ItemNode("Molten Essence");
        molten2.addChild(new ItemNode("Vitality Crystal"));
        athenasShield.addChild(molten2);
        athenasShield.addChild(new ItemNode("Vitality Crystal"));
        athenasShield.addChild(new ItemNode("Magic Resist Cloak"));

        // Antique Cuirass — Bahan: Vitality Crystal + Dreadnaught Armor + Steel Legplates
        ItemNode antiqueCuirass = new ItemNode("Antique Cuirass");
        antiqueCuirass.addChild(new ItemNode("Vitality Crystal"));
        ItemNode dreadAntique = new ItemNode("Dreadnaught Armor");
        dreadAntique.addChild(new ItemNode("Leather Jerkin"));
        dreadAntique.addChild(new ItemNode("Leather Jerkin"));
        antiqueCuirass.addChild(dreadAntique);
        antiqueCuirass.addChild(new ItemNode("Steel Legplates"));

        // Oracle — Bahan: Ares Belt + Healing Necklace + Magic Resist Cloak
        ItemNode oracle = new ItemNode("Oracle");
        oracle.addChild(new ItemNode("Ares Belt"));
        oracle.addChild(new ItemNode("Healing Necklace"));
        oracle.addChild(new ItemNode("Magic Resist Cloak"));

        // TASK 5 — Tambah satu level lebih dalam pada Dreadnaught Armor
        // Sebelumnya: Dreadnaught Armor -> Leather Jerkin (3 level)
        // Sekarang:   Dreadnaught Armor -> Leather Jerkin -> Raw Hide (4 level -> jadi 5 level total)
        // Ini menambah tinggi pohon dari 4 menjadi 5
        ItemNode dreadnaught5 = new ItemNode("Dreadnaught Armor");
        ItemNode leatherJerkinDeep1 = new ItemNode("Leather Jerkin");
        leatherJerkinDeep1.addChild(new ItemNode("Raw Hide")); // Level baru (sub-bahan paling dasar)
        ItemNode leatherJerkinDeep2 = new ItemNode("Leather Jerkin");
        leatherJerkinDeep2.addChild(new ItemNode("Raw Hide")); // Level baru
        dreadnaught5.addChild(leatherJerkinDeep1);
        dreadnaught5.addChild(leatherJerkinDeep2);

        // Blade Armor — item dengan Dreadnaught Armor yang sudah ditambah levelnya
        // Ini membuktikan tinggi pohon bertambah saat ada level baru
        ItemNode bladeArmor = new ItemNode("Blade Armor");
        bladeArmor.addChild(dreadnaught5);
        bladeArmor.addChild(new ItemNode("Steel Legplates"));

        // Sambungkan semua item ke root
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
        root.addChild(bladeArmor); // TASK 5: item baru dengan level tambahan

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
        // TASK 2 — Hitung kemunculan item tertentu
        // ════════════════════════════════════════
        System.out.println();
        System.out.println("=== TASK 2: JUMLAH KEMUNCULAN ITEM ===");
        String[] itemDicek = {"Vitality Crystal", "Ares Belt", "Leather Jerkin", "Magic Resist Cloak"};
        for (String item : itemDicek) {
            int jumlah = countItemOccurrences(root, item);
            System.out.println("\"" + item + "\" muncul sebanyak: " + jumlah + " kali");
        }

        // ════════════════════════════════════════
        // TASK 3 — Cetak jalur yang berakhir dengan "Immortality"
        // ════════════════════════════════════════
        System.out.println();
        System.out.println("=== TASK 3: JALUR YANG BERAKHIR DENGAN 'Immortality' ===");
        printPathsEndingWith(root, "Immortality", new ArrayList<>());

        // ════════════════════════════════════════
        // TASK 5 — Observasi tinggi pohon sebelum & sesudah level baru
        // ════════════════════════════════════════
        System.out.println();
        System.out.println("=== TASK 5: OBSERVASI TINGGI POHON ===");
        System.out.println("Tinggi pohon saat ini    : " + height(root));
        System.out.println("(Blade Armor menambah level: Blade Armor -> Dreadnaught Armor -> Leather Jerkin -> Raw Hide)");
        System.out.println("Tinggi bertambah karena ada sub-bahan baru 'Raw Hide' di level terdalam");

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
        // TASK 4 — Scanner untuk input nama item target pencarian (sudah ada sejak awal)
        System.out.println("Perintah: 'history' | 'clear' | 'exit'");
        System.out.println("Maksimal item dalam build: 6 slot");

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
}