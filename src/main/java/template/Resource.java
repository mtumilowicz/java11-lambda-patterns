package template;

import lombok.Value;

import java.util.function.Consumer;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
@Value
class Resource implements AutoCloseable {
    private Resource(String param) {
        System.out.println("create");
    }

    void op1() {
        System.out.println("op1");
    }

    void op2() {
        System.out.println("op2");
    }

    static void use(String param, Consumer<Resource> block) {
        try (final var resource = new Resource(param)) {
            block.accept(resource);
        }
    }

    @Override
    public void close() {
        System.out.println("close");
    }
}
