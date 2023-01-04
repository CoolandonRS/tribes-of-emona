package net.numra.emonatribes.tribes.hooks.sound.speak;

import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.Value;
import net.numra.emonatribes.tribes.hooks.sound.IncompatibleSpeakException;

import java.util.ArrayList;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = false)
public class MultiSpeak extends GenericSpeak {
    List<? extends GenericSpeak> speaks;

    public List<? extends GenericSpeak> getSpeaks() {
        return new ArrayList<>(speaks);
    }

    @SneakyThrows
    public MultiSpeak(List<? extends GenericSpeak> list) {
        List<List<GenericSpeak>> megaList = new ArrayList<>();
        int size = -1;
        for (GenericSpeak gs : list) {
            if (gs instanceof MultiSpeak ms) {
                if (size == -1) size = ms.speaks.size();
                if (ms.speaks.size() != size) throw new IncompatibleSpeakException();
                var iterator = ms.speaks.listIterator();
                while (iterator.hasNext()) {
                    int idx = iterator.nextIndex();
                    GenericSpeak speak = iterator.next();
                    try {
                        if (speak.getClass().isAssignableFrom(megaList.get(idx).get(0).getClass())) {
                            megaList.get(idx).add(speak);
                        } else throw new IncompatibleSpeakException();
                    } catch (IndexOutOfBoundsException e) {
                        megaList.add(new ArrayList<>());
                        megaList.get(idx).add(speak);
                    }
                }
            } else throw new IncompatibleSpeakException();
        }
        this.speaks = new ArrayList<>();
        for (List<GenericSpeak> ls : megaList) {
            this.speaks.add(ls.get(0).getClass().getConstructor(List.class).newInstance(ls));
        }
    }
}
