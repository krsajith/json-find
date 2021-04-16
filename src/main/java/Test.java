import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("src/main/resources/test.json")));
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(content, new TypeToken<Map<String, Object>>() {
        }.getType());

        System.out.println(map);

//

        String expression = "DailyForecasts.Temperature.Minimum.UnitType";
        ArrayList<String> keys = new ArrayList<>(Arrays.asList(expression.split("\\.")));
        remove(map, keys);
    }

    private static void remove(Map<?, ?> map, List<String> keys) {
        Object current = map;

//        String last = keys.remove(keys.size() - 1);
//        System.out.println(keys);
//        System.out.println(last);
        int index = 0;
        for (String token : keys) {
            index++;
            if (current instanceof Map<?, ?> currentMap) {
                current = currentMap.get(token);
                System.out.println(token + "=" + current);
                continue;
            }

            if (current instanceof List<?> currentList) {
                System.out.println("Found arr " + token);
                for (Object o : currentList) {
                    if (o instanceof Map<?, ?> currentMap) {
                        List<String> remainingKeys = keys.subList(index, keys.size());
                        System.out.println("Removing " + remainingKeys + o);
                        remove((Map<?, ?>) currentMap.get(token), remainingKeys);
                        current = currentMap.get(token);
                        System.out.println(current);
                    }
                }
            }
        }
    }
}
