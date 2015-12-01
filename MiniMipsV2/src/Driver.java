
import UI.MipsUI;

public class Driver {
    public static void main(String args[]) {
        MipsUI frame = new MipsUI();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(frame.MAXIMIZED_BOTH);
    }
}
