import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer {
    public static void main(String [] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            System.out.println("Server listening on port 9999....");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected: "+ clientSocket.getInetAddress().getHostAddress());

                receiveFile(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveFile(Socket clientSocket) {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            String fileName = dataInputStream.readUTF();
            long fileSize = dataInputStream.readLong();

            System.out.println("Receiving File:" + fileName);

            FileOutputStream fileOutputStream = new FileOutputStream("Received_" + fileName);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);

            }

            System.out.println("File Received Succesfully.");
            fileOutputStream.close();
            dataInputStream.close();
            inputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}