import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BurningForestSimulation extends JPanel {

    private char[][] map;
    private int size;
    private double forestation;
    private List<int[]> container1 = new ArrayList<>();
    private List<int[]> container2 = new ArrayList<>();

    public static BurningForestSimulation forest = new BurningForestSimulation(100, 0.55);

    public BurningForestSimulation(int size, double forestation) {
        this.size = size;
        this.forestation = forestation;
        map = new char[size][size];
    }

    public void map_initialization() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double chance = new Random().nextDouble();
                if (chance < forestation) {
                    map[i][j] = 'T';
                } else {
                    map[i][j] = 'X';
                }
            }
        }

        print_map();
    }

    public void fire_initialization() {
        for (int j = 0; j < size; j++) {
            if (Objects.equals(map[0][j], 'T')) {
                map[0][j] = 'B';
                container1.add(new int[]{0, j});
            }
        }

        print_map();
    }

    public void make_simulation1() {
        if (container1.isEmpty())
            return;

        for (int[] B : container1) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    try {
                        if (Objects.equals(map[B[0] + i][B[1] + j], 'T')) {
                            map[B[0] + i][B[1] + j] = 'B';
                            container2.add(new int[]{B[0] + i, B[1] + j});
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }

        print_map();
        container1.clear();
        make_simulation2();
    }

    public void make_simulation2() {
        if (container2.isEmpty())
            return;

        for (int[] B : container2) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    try {
                        if (Objects.equals(map[B[0] + i][B[1] + j], 'T')) {
                            map[B[0] + i][B[1] + j] = 'B';
                            container1.add(new int[]{B[0] + i, B[1] + j});
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }

        print_map();
        container2.clear();
        make_simulation1();
    }

    public void print_map() {
        forest.repaint();

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
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.black);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == 'T') {
                    g.setColor(new Color(34, 139, 34));
                    g.fillRect(5 * j + 2, 5 * i + 2, 3, 3);
                } else if (map[i][j] == 'B') {
                    g.setColor(Color.orange);
                    g.fillRect(5 * j + 2, 5 * i + 2, 3, 3);
                }
            }
        }

        g.drawString("", 0, 550);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Burning Forest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(forest);
        frame.setSize(600, 600);
        frame.setVisible(true);

        forest.map_initialization();
        forest.fire_initialization();
        forest.make_simulation1();
    }
} 