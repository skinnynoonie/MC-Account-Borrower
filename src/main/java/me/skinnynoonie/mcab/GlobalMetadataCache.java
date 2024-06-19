package me.skinnynoonie.mcab;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class GlobalMetadataCache {

    private static final Map<String, Object> metadataMap = new ConcurrentHashMap<>();

    public static void setMetadata(String id, Object data) {
        metadataMap.put(id, data);
    }

    public static Object getMetadata(String id) {
        return metadataMap.get(id);
    }

    private GlobalMetadataCache() {
    }

}
