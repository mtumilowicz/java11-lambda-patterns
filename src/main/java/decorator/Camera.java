package decorator;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.awt.*;
import java.util.function.Function;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
@Value
@RequiredArgsConstructor
public class Camera {
    Function<Color, Color> transformColors;

    public Camera() {
        this.transformColors = Function.identity();
    }

    Camera withFilter(Function<Color, Color> transform) {
        return new Camera(transformColors.andThen(transform));
    }

    public Color snap(Color color) {
        return transformColors.apply(color);
    }
}
