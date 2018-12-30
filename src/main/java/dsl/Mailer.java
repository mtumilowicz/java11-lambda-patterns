package dsl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

import java.util.function.UnaryOperator;

/**
 * Created by mtumilowicz on 2018-11-30.
 */
@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Mailer {
    private static final Mailer EMPTY = new Mailer();

    String from;
    String to;

    private Mailer() {
        this.from = "";
        this.to = "";
    }

    Mailer from(String from) {
        return new Mailer(StringUtils.defaultIfEmpty(from, ""), to);
    }

    Mailer to(String to) {
        return new Mailer(from, StringUtils.defaultIfEmpty(to, ""));
    }

    static void send(UnaryOperator<Mailer> block) {
        System.out.println(block.apply(EMPTY));
    }
}
