package expense;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.collections4.SetUtils;

import java.time.Year;
import java.util.stream.Stream;

/**
 * Created by mtumilowicz on 2018-11-24.
 */
@Value
@Builder
public class Expense {
    Year year;
    ImmutableSet<String> tags;

    public Stream<String> getTagsStream() {
        return SetUtils.emptyIfNull(tags).stream();
    }
}
