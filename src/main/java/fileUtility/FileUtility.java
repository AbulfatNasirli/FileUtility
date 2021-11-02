/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fileUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author DELL
 */

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author DELL
 */
public class FileUtility {

private static void writeIntoFile(String fileName, String text, boolean append) throws Exception {
        FileWriter fw = new FileWriter(fileName, append);
        try (BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(text);
            bw.flush();
            bw.close();
        }
    }

    public static void writeIntoFile(String fileName, String text) throws Exception {
        FileUtility.writeIntoFile(fileName, text, true);
    }

    public static void appendIntoFile(String fileName, String text) throws Exception {
        FileUtility.writeIntoFile(fileName, text, false);
    }

    public static String read(String fileName) throws Exception {
        InputStream in = new FileInputStream(fileName);
        InputStreamReader ss = new InputStreamReader(in);
        BufferedReader bf = new BufferedReader(ss);
        String line = null;
        String result = "";
        while ((line = bf.readLine()) != null) {
            result += line + "\n";
        }
        return result;
    }

    public static byte[] readBytes(String fileName) throws Exception {
        File file = new File(fileName);

        try (FileInputStream fileInputStream = new FileInputStream(file)) {

            byte[] bytesArray = new byte[(int) file.length()];
            fileInputStream.read(bytesArray);
            return bytesArray;
        }
    }

    public static void writeIntoByte(String fileName, byte[] data) throws Exception {
        File file = new File(fileName);
        FileOutputStream fop = new FileOutputStream(file);
        fop.write(data);
        fop.flush();
        fop.close();
        System.out.println("Done");
    }

    public static void writeByte(byte[] data, String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        byte[] byteArray = Files.readAllBytes(filePath);
        Path fileCopy = FileSystems.getDefault().getPath(".", "tempCopy.txt");
        Files.write(fileCopy, byteArray);
    }

    public static byte[] readBytesNio(String fileName) throws IOException {
        Path filePath = Paths.get(fileName);
        byte[] byteArray = Files.readAllBytes(filePath);
        return byteArray;
    }

    public static Object readObject(String fileName) throws IOException, ClassNotFoundException {
        Object obj = null;
        FileInputStream fip = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fip);
        obj = objectInputStream.readObject();
        return obj;
    }

    public static void writeObject(Serializable object, String fileName) throws Exception {
        FileOutputStream fout = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fout);
        out.writeObject(object);
    }

    private static void fromWeb(String fromFile, String toFile) throws Exception {
        URL url = new URL(fromFile);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        InputStream in = con.getInputStream();

        ReadableByteChannel rbc = Channels.newChannel(in);
        FileOutputStream fos = new FileOutputStream(toFile);
        fos.getChannel().transferFrom(rbc,0,Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
    
}

    

