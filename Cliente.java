import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))) {
            String userInput;
            while (true) {
                System.out.print("Ingrese dos números y un operador (+, -, *, /) separados por espacio (o 'salir' para terminar): ");
                userInput = stdIn.readLine();
                out.println(userInput);
                if (userInput.equalsIgnoreCase("salir")) {
                    break;
                }
                System.out.println("Respuesta del servidor: " + in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
