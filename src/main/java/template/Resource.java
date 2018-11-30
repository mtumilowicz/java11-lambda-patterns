package template;

import lombok.Value;

import java.util.function.Consumer;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
@Value
public class Resource implements AutoCloseable {
    private Resource(String param) {
    }

    public void op1() {

    }

    public void op2() {

    }

    public void use(String param, Consumer<Resource> block) {
        try (final Resource resource = new Resource(param)) {
            block.accept(resource);
        }
    }

    public void close() {

    }
}