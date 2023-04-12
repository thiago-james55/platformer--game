package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {

        BufferedImage bufferedImage = null;

        try (InputStream is = LoadSave.class.getResourceAsStream("/" + fileName)) {
            bufferedImage = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bufferedImage;

    }

    public static int[][] GetLevelData(){
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage bufferedImage = GetSpriteAtlas(LEVEL_ONE_DATA);

        for (int i = 0; i < bufferedImage.getHeight(); i++ ) {
            for (int j = 0; j < bufferedImage.getWidth(); j++ ) {
                Color color = new Color(bufferedImage.getRGB(j,i));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                levelData[i][j] = value;
            }
        }

        return levelData;
    }

}
