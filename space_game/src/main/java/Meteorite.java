import java.util.Random;

public class Meteorite {
    public int x;
    public int y;
    public int size;
    public int dx = 1;
    public int dy = 1;

    public Meteorite() {
        Random random = new Random();
        int direction = random.nextInt(4);

        size = (random.nextInt(10) + 1) * 10;

        //from the left
        if(direction == 0){
            x =0;
            y = random.nextInt(1000);

            dx = random.nextInt(15) + 1;
            dy = random.nextInt(15) - 7;
        }
        //from the top
        else if (direction == 1){
            y = 0;
            x = random.nextInt(1000);

            dx = random.nextInt(15) - 7;
            dy = random.nextInt(15) + 1;
        }
        //from the down
        else if (direction == 2){
            y = 1000;
            x = random.nextInt(1000);

            dx = random.nextInt(15) - 7;
            dy = random.nextInt(15) - 7;
        }
        //from the right
        else if (direction == 3){
            x = 1000;
            y = random.nextInt(1000);

            dx = random.nextInt(15) - 7;
            dy = random.nextInt(15) - 7;
        }
    }

    public void meteorMotion(){
        x += dx;
        y += dy;
    }

    public boolean isItVisible(){
        return x + size >= 0 && y + size >= 0 && x < 1000 && y < 1000;
    }

}
