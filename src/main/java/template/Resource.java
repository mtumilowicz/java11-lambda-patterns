package template;

import lombok.Value;

import java.util.function.Consumer;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
@Value
public class Resource implements AutoCloseable {
    private Resource(String param) {
        System.out.println("create");
    }

    public void op1() {
        System.out.println("op1");
    }

    public void op2() {
        System.out.println("op2");
    }

    public static void use(String param, Consumer<Resource> block) {
        try (final var resource = new Resource(param)) {
            block.accept(resource);
        }
    }

    @Override
    public void close() {
        System.out.println("close");
    }
}
