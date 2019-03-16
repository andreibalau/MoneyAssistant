package moneyassistant.expert.model.entity;

public class LegendModel {

    private int color;
    private String name;
    private float percent;

    public LegendModel(int color, String name, float percent) {
        this.color = color;
        this.name = name;
        this.percent = percent;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
