package chapter09;

/**
 * @author : YINAN
 * @date : 2023/8/14
 * @effect :
 */
public class Point {

    private final int x;

    private final int y;

    private Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }

    public Point moveRightBy(int x){
        return new Point(this.x + x, this.y);
    }
}
