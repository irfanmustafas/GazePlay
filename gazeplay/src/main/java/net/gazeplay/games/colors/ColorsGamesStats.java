package net.gazeplay.games.colors;

import java.io.IOException;
import javafx.scene.Scene;
import lombok.extern.slf4j.Slf4j;
import net.gazeplay.commons.utils.stats.SavedStatsInfo;
import net.gazeplay.commons.utils.stats.SelectionGamesStats;

/**
 * Stats for the color game.
 * 
 * @author Thomas Medard
 */
@Slf4j
public class ColorsGamesStats extends SelectionGamesStats {

    public ColorsGamesStats(Scene gameContextScene) {
        super(gameContextScene);
        this.gameName = "Colors";
    }

    @Override
    public SavedStatsInfo saveStats() throws IOException {

        SavedStatsInfo statsInfo = super.saveStats();

        log.info("Stats saved");
        return statsInfo;
    }
}
