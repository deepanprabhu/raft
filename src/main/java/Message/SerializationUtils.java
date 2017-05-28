package Message;

import java.io.*;

public class SerializationUtils {
    public static byte[] serialize(Object object){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutput oo = new ObjectOutputStream(baos);
            oo.writeObject(object);
            oo.flush();
            return baos.toByteArray();
        }
        catch(Exception e){
            System.out.println("Error Serializing " + e.getMessage());
        }
        return null;
    }
    public static Object deserialize(byte[] bytes){
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput oi = new ObjectInputStream(bis);
            return oi.readObject();
        }
        catch(Exception e){
            System.out.println("Error DeSerializing " + e.getMessage());
        }
        return null;
    }
}
