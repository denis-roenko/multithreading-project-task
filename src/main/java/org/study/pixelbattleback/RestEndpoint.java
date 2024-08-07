package org.study.pixelbattleback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.study.pixelbattleback.dto.PixelRequest;
import org.study.pixelbattleback.dto.ResultResponse;
import org.study.pixelbattleback.mapservice.NonBlockingMapService;

@RestController
public class RestEndpoint {

    @Autowired
    private NonBlockingMapService mapService;

    /**
     * Окрашивание пикселя в выбранный цвет
     *
     * @param request
     * @return
     */
    @PostMapping("/drawPixel")
    public ResultResponse pixel(@RequestBody PixelRequest request) {
        if (!mapService.draw(request)) {
            return ResultResponse.error("Не удалось окрасить пиксель");
        }
        return ResultResponse.pixel(request);
    }

    /**
     * Получение карты целиком
     *
     * @return
     */
    @GetMapping("/load")
    public ResultResponse load() {
        return ResultResponse.map(mapService.getMap());
    }
}
