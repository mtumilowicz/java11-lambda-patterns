package decorator;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.awt.*;
import java.util.function.Function;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
@Value
@RequiredArgsConstructor
class Camera {
    
    @Getter(AccessLevel.NONE)
    Function<Color, Color> transformColors;

    Camera() {
        this.transformColors = Function.identity();
    }

    Camera withFilter(Function<Color, Color> transform) {
        return new Camera(transformColors.andThen(transform));
    }

    Color snap(Color color) {
        return transformColors.apply(color);
    }
}
