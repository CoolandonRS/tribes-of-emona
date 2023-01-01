package net.numra.emonatribes.tribes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TribeDataAuthority {
    private Map<UUID, TribeData> map;

    public TribeData get(UUID uuid) {
        return map.get(uuid);
    }

    public boolean register(TribeData td) {
        if (map.containsKey(td.uuid())) return false;
        map.put(td.uuid(), td);
        return true;
    }

    public void unregister(TribeData td) {
        map.remove(td.uuid());
    }

    public TribeDataAuthority() {
        map = new HashMap<>();
    }
}
