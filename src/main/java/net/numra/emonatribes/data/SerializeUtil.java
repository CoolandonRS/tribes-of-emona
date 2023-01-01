package net.numra.emonatribes.data;

import net.numra.emonatribes.ModConstants;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.function.Supplier;

public final class SerializeUtil {
    public static <T> T deserialize(Class<T> clazz, File file, @Nullable String errMsg, Supplier<T> failHandler) {
        if (!file.exists()) return failHandler.get();
        try (FileInputStream fis = new FileInputStream(file)) {
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                Object obj = ois.readObject();
                if (!obj.getClass().isAssignableFrom(clazz)) throw new Exception("jump to catch");
                return clazz.cast(obj);
            }
        } catch (Exception e) {
            if (errMsg != null) ModConstants.logger.error(errMsg);
            return failHandler.get();
        }
    }

    public static <T> void serialize(T obj, File file, @Nullable String errMsg) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(obj);
            }
        } catch (Exception e) {
            if (errMsg != null) ModConstants.logger.error(errMsg);
        }
    }
}
