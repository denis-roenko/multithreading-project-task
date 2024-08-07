package org.study.pixelbattleback.mapservice;

import org.springframework.stereotype.Service;
import org.study.pixelbattleback.dto.PixelRequest;

import java.util.Arrays;

@Service
public class MapService extends BaseMapService {

    private final int[] colors;

    /**
     * Пытаемся загрузить карту из файла на старте, или же начинаем с пустой карты
     */
    public MapService() {
        super();
        colors = tmp.getColors();
    }

    /**
     * Окрашивание пикселя
     *
     * @param pixel
     * @return
     */
    public synchronized boolean draw(PixelRequest pixel) {
        int x = pixel.getX();
        int y = pixel.getY();
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }
        colors[y * width + x] = pixel.getColor();
        isChanged = true;
        return true;
    }

    /**
     * Чтение всей карты
     *
     * @return
     */
    protected synchronized int[] getColors() {
        return Arrays.copyOf(colors, colors.length);
    }
}
