import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado...");
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ManejadorCliente(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ManejadorCliente implements Runnable {
    private Socket socket;

    public ManejadorCliente(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Nuevo cliente conectado: " + socket.getInetAddress());
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equalsIgnoreCase("salir")) {
                    break;
                }
                String[] parts = inputLine.split(" ");
                double num1 = Double.parseDouble(parts[0]);
                double num2 = Double.parseDouble(parts[1]);
                String operador = parts[2];
                double resultado = 0;

                switch (operador) {
                    case "+":
                        resultado = num1 + num2;
                        break;
                    case "-":
                        resultado = num1 - num2;
                        break;
                    case "*":
                        resultado = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            resultado = num1 / num2;
                        } else {
                            out.println("Error: División por cero");
                            continue;
                        }
                        break;
                    default:
                        out.println("Operador no válido");
                        continue;
                }
                out.println("Resultado: " + resultado);
                System.out.println("Operación realizada: " + num1 + " " + operador + " " + num2 + " = " + resultado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Cliente desconectado: " + socket.getInetAddress());
        }
    }
}
