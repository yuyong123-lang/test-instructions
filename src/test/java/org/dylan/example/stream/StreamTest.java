package org.dylan.example.stream;

import lombok.Data;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StreamOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dylan
 * @version 2024/5/27
 */
@SpringBootTest
public class StreamTest {

    public static final String STREAM_NAME = "mytask";
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void stream_xadd_test() {
        final StreamOperations<String, String, String> streamOps = redisTemplate.opsForStream();
        final HashMap<String, String> neededSaveValue = new HashMap<>();
        neededSaveValue.put("id", "3");
        neededSaveValue.put("name", "whzijm");
        streamOps.add(STREAM_NAME, neededSaveValue);
    }


    @Test
    public void stream_xadd_object_test() {
        final StreamOperations<String, String, Person> streamOps = redisTemplate.opsForStream();
        final Map<String, Person> neededSaveValue = new HashMap<>();
        neededSaveValue.put("person", new Person().setName("dylan"));
        streamOps.add(STREAM_NAME, neededSaveValue);
    }

    @Test
    public void stream_xrange_test() {
        final StreamOperations<String, Object, Object> streamOps = redisTemplate.opsForStream();
        final List<MapRecord<String, Object, Object>> result = streamOps.range(
                STREAM_NAME,
                Range.from(Range.Bound.inclusive("-")).to(Range.Bound.inclusive("+")),
                RedisZSetCommands.Limit.limit().count(1)
        );
        System.out.println(result);
    }

    @Data
    @Accessors(chain = true)
    public static class Person {
        String name;
    }
}
