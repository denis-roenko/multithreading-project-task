package org.study.pixelbattleback.mapservice;

import org.springframework.stereotype.Service;
import org.study.pixelbattleback.dto.PixelRequest;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class NonBlockingMapService extends BaseMapService {

    private final AtomicInteger[] colors;

    /**
     * Пытаемся загрузить карту из файла на старте, или же начинаем с пустой карты
     */
    public NonBlockingMapService() {
        super();
        colors = Arrays.stream(tmp.getColors())
                .mapToObj(AtomicInteger::new)
                .toArray(AtomicInteger[]::new);
    }

    /**
     * Окрашивание пикселя
     *
     * @param pixel
     * @return
     */
    public boolean draw(PixelRequest pixel) {
        int x = pixel.getX();
        int y = pixel.getY();
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return false;
        }

        while (true) {
            int currentValue = colors[y * width + x].get();
            if (colors[y * width + x].compareAndSet(currentValue, pixel.getColor())) {
                isChanged = true;
                return true;
            }
        }
    }

    /**
     * Чтение всей карты
     *
     * @return
     */
    protected int[] getColors() {
        return Arrays.stream(colors)
                .mapToInt(AtomicInteger::get)
                .toArray();
    }
}
