import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;


public class Cosmos extends JPanel {
    int x = 100;
    int y = 100;
    int points = 0;

    ArrayList<Meteorite> meteorites = new ArrayList<>();

    public Cosmos(){
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if(crashWithTheMouseCursor(e.getX(), e.getY())){
                JOptionPane.showMessageDialog(null, String.format("You get %d points", ((int)points)));
                points = 0;
                meteorites.clear();
                repaint();
                }

                while (x !=  e.getX() || y != e.getY()){
                 x += e.getX() - x;
                 y += e.getY() - y;

                 repaint();

                 points += 1;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try{
                        Thread.sleep(250);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    meteorites.add(new Meteorite());
                }

            }
        }).start();

    }

    public static BufferedImage resize(BufferedImage img, int height, int width){
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage scale = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB );
        Graphics2D g2d = scale.createGraphics();
        g2d.drawImage(tmp, 0, 0,null);
        g2d.dispose();
        return scale;

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 1000);
    }

    @Override
    protected void paintComponent(Graphics g){
            super.paintComponent(g);
            try {
                g.drawImage(ImageIO.read(new File("C:\\Users\\noemi\\IdeaProjects\\simple_space_game\\space_game\\src\\main\\java\\tlo.png")),0, 0, null);
                g.drawImage(ImageIO.read(new File("C:\\Users\\noemi\\IdeaProjects\\simple_space_game\\space_game\\src\\main\\java\\ufo.png")),x - 25,y - 25,null);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            g.setColor(Color.yellow);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(points +"", 950, 30);

            Meteorite[] meteoritesTab = new Meteorite[meteorites.size()];
            meteorites.toArray(meteoritesTab);

           for (Meteorite meteorite: meteoritesTab){
               try{
                   meteorite.meteorMotion();
                   try {
                       g.drawImage(resize((BufferedImage) ImageIO.read(new File("C:\\Users\\noemi\\IdeaProjects\\simple_space_game\\space_game\\src\\main\\java\\meteor.png")), meteorite.size, meteorite.size),
                               meteorite.x, meteorite.y, null);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
        }

        public boolean crashWithTheMouseCursor(int mouseX, int mouseY){
        Meteorite[] meteoritesTab = new Meteorite[meteorites.size()];
        meteorites.toArray(meteoritesTab);
        for (Meteorite meteorite : meteoritesTab){

            if (!meteorite.isItVisible())
                meteorites.remove(meteoritesTab);
            if(meteorite.x <= mouseX && meteorite.y <= mouseY && meteorite.x + meteorite.size >= mouseX &&
                    meteorite.y + meteorite.size >= mouseY)
                return true;
        }
        return  false;
        }

    public static void main(String[] args) {

        JFrame window = new JFrame("Galaxy");

        Cosmos cosmos = new Cosmos();
        window.add(cosmos);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();

    }

}
