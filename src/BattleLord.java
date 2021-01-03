
public class BattleLord extends Sprite {
    public static final String[] name = {"bl_up_", "bl_dn_", "bl_lt_", "bl_rt_"};

    public BattleLord(double x, double y, int action) {
        super(x, y, action, name, 10, 5, ".gif");
    }

}