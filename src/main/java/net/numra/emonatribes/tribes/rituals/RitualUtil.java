package net.numra.emonatribes.tribes.rituals;

import net.minecraft.item.ItemStack;
import net.numra.emonatribes.ModConstants;
import net.numra.emonatribes.tribes.*;

import java.util.*;
import java.util.stream.Collectors;

public final class RitualUtil {
    public static Map<TribeRecord, List<TribalRitualType>> getCastableRituals(TribeData data) {
        Map<TribeRecord, List<TribalRitualType>> out = new HashMap<>();
        TribeRecord name = data.getTribeName();
        Tribe values = data.getTribeValues();
        GlobalTribeAuthority gta = ModConstants.globalTribeAuthority;
        if (name == TribeRecord.None) {
            EnumMap<TribeRecord, GlobalTribeAuthority.Data> map = gta.get();
            map.remove(TribeRecord.None);
            map.forEach((key, value) -> out.put(key, List.of(value.created() ? TribalRitualType.Membership : TribalRitualType.Creation)));
            return out;
        }
        int level = values.getLevel();
        out.put(name, new ArrayList<>());
        out.get(name).add(TribalRitualType.Founding);
        if (level < 5) {
            out.get(name).add(TribalRitualType.Curious);
        } else if (level < 10) {
            out.get(name).add(TribalRitualType.Student);
        } else if (level < 15) {
            out.get(name).add(TribalRitualType.Expert);
        } else if (level < 20) {
            out.get(name).add(TribalRitualType.Master);
        } else if (level < 25) {
            out.get(name).add(TribalRitualType.Ascended);
        } else if (level == 25 && gta.hasHadGod(name) && !data.isLeader()) {
            out.get(name).add(TribalRitualType.Demigod);
        } else if (level == 25 && !gta.hasGod(name) && data.isLeader()) {
            out.get(name).add(TribalRitualType.Godhood);
        }
        return out;
    }

    public static Map<TribeRecord, List<TribalRitualType>> getCastableRitualsWithItemStack(TribeData data, ItemStack payment) {
        // THE ABSOLUTELY MASSIVE ONE LINER
        // Why is it one line? Because it can be.
        // Should it be more than one? Absolutely.
        // Am I going to make it more than one? Never! (Unless it breaks)
        // Is it ironic that this comment has multiple lines and the function doesn't? Maybe...
        // But it's perfect in the worst way possible.
        // We just hope it never breaks :)
        return getCastableRituals(data).entrySet().stream().peek(e -> e.setValue(e.getKey().getRituals().entrySet().stream().filter(ee -> e.getValue().contains(ee.getKey()) && ee.getValue().isItemEqual(payment) && ee.getValue().getCount() <= payment.getCount()).map(Map.Entry::getKey).toList())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).entrySet().stream().filter(e -> !e.getValue().isEmpty()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
