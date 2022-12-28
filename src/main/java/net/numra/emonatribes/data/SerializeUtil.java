package net.numra.emonatribes.data;

import java.io.*;
import java.nio.file.Path;

public class SerializeUtil {
    public static <T extends Serializable> void save(T data, Path path) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(data);
            }
        }
    }

    public static Object read(Path path) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                return ois.readObject();
            }
        }
    }
}
