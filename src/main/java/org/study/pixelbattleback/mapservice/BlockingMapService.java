package org.study.pixelbattleback.mapservice;

import org.springframework.stereotype.Service;
import org.study.pixelbattleback.dto.PixelRequest;

import java.util.Arrays;

@Service
public class BlockingMapService extends BaseMapService {
    private final SyncElement<Integer>[] colors;

    /**
     * Пытаемся загрузить карту из файла на старте, или же начинаем с пустой карты
     */
    public BlockingMapService() {
        super();
        colors = Arrays.stream(tmp.getColors())
                .mapToObj(SyncElement::new)
                .toArray(SyncElement[]::new);
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

        colors[y * width + x].set(pixel.getColor());
        isChanged = true;
        return true;
    }

    /**
     * Чтение всей карты
     *
     * @return
     */
    protected int[] getColors() {
        return Arrays.stream(colors)
                .mapToInt(SyncElement::get)
                .toArray();
    }

    private static class SyncElement<T> {
        private T value;

        public SyncElement(T value) {
            this.value = value;
        }

        public synchronized T get() {
            return value;
        }

        public synchronized void set(T value) {
            this.value = value;
        }
    }
}


