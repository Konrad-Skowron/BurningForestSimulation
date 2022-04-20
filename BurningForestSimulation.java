import java.util.*;

public class BurningForestSimulation {

    private String[][] map;
    private int size;
    private double forestation;
    private List<int[]> container1 = new ArrayList<>();
    private List<int[]> container2 = new ArrayList<>();

    public BurningForestSimulation(int size, double forestation) {
        this.size = size;
        this.forestation = forestation;
        map = new String[size][size];
    }

    public void map_initialization() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double chance = new Random().nextDouble();
                if (chance < forestation) {
                    map[i][j] = "T";
                } else {
                    map[i][j] = ".";
                }
            }
        }
        print_map();
    }

    public void fire_initialization() {
        for (int j = 0; j < size; j++) {
            if (Objects.equals(map[0][j], "T")) {
                map[0][j] = "F";
                container1.add(new int[]{0, j});
            }
        }
        print_map();
    }

    public void make_simulation1() {
        if (container1.isEmpty()) return;
        for (int[] B : container1) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    try {
                        if (Objects.equals(map[B[0] + i][B[1] + j], "T")) {
                            map[B[0] + i][B[1] + j] = "F";
                            container2.add(new int[]{B[0] + i, B[1] + j});
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        container1.clear();
        print_map();
        make_simulation2();
    }

    public void make_simulation2() {
        if (container2.isEmpty()) return;
        for (int[] B : container2) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    try {
                        if (Objects.equals(map[B[0] + i][B[1] + j], "T")) {
                            map[B[0] + i][B[1] + j] = "F";
                            container1.add(new int[]{B[0] + i, B[1] + j});
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        container2.clear();
        print_map();
        make_simulation1();
    }

    public void print_map() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        BurningForestSimulation forest = new BurningForestSimulation(50, 0.55);

        forest.map_initialization();
        forest.fire_initialization();
        forest.make_simulation1();
    }
}