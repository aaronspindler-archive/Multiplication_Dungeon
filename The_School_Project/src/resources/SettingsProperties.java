package resources;

/**
 *
 * @author xNovax
 */
public class SettingsProperties {

    boolean debugModeG;
    String gameName;

    public SettingsProperties() {
        debugModeG = true;
        gameName = "Multiplication Dungeon";
    }

    public boolean getDebugMode() {
        return debugModeG;
    }

    public String getGameName() {
        return gameName;
    }
}
