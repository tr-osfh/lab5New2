package root.files.seClasses;

import java.util.Objects;

public class Coordinates {
    private Float x; //Поле не может быть null
    private Integer y; //Поле не может быть null

    public Coordinates(Float x, Integer y){
        if (x == null || y == null){
            System.out.println("Вкралась ошибка1");
        } else {
            this.x = x;
            this.y = y;
        }
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Coordinates that = (Coordinates) object;
        return
                Objects.equals(x, that.x) &&
                Objects.equals(y, that.y);
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                x,
                y
        );
    }
}
