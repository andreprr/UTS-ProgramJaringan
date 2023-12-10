import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;




public class FileClient {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java FileClient <path_to_file>");
            return;
        }

        String filePath = args[0];

        try {
            Socket socket = new Socket("LocalHost", 9999);
            
            sendFile(socket, filePath);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(Socket socket, String filePath) {
    try {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        dataOutputStream.writeUTF(file.getName());
        dataOutputStream.writeLong(file.length());

        byte[] buffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        System.out.println("File sent succesfully.");
        bufferedInputStream.close();
        fileInputStream.close();
        dataOutputStream.close();
        outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
         }
    }
}