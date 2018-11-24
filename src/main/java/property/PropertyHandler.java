package property;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.collections4.ListUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mtumilowicz on 2018-11-24.
 */
public class PropertyHandler {
    private final ImmutableMap<String, String> properties;

    public PropertyHandler(ImmutableMap<String, String> properties) {
        this.properties = Objects.requireNonNull(properties);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(properties.get(key));
    }
    
    public String propertiesOf(ImmutableList<String> keys) {
        return ListUtils.emptyIfNull(keys).stream()
                .map(this::get)
                .flatMap(Optional::stream)
                .collect(Collectors.joining(","));
    }
}
