import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {

    static StudentMarks classRoom = new StudentMarks();

    public static void main(String[] args) throws Exception {

        // Create Java web server
        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080), 0
        );

        // Open the website
        server.createContext("/", Main::handleWebsite);

        // Receive student data
        server.createContext("/addStudent", Main::addStudent);

        server.setExecutor(null);

        server.start();

        System.out.println("Student Marks Server Started!");
        System.out.println("Open: http://localhost:8080");
    }


    // =========================================
    // OPEN HTML, CSS AND JAVASCRIPT FILES
    // =========================================

    static void handleWebsite(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();

        if (path.equals("/")) {
            path = "/index.html";
        }

        File file = new File("Web" + path);

        if (!file.exists()) {

            String message = "File not found";

            exchange.sendResponseHeaders(404, message.length());

            OutputStream output = exchange.getResponseBody();

            output.write(message.getBytes());

            output.close();

            return;
        }

        String contentType = "text/html";

        if (path.endsWith(".css")) {
            contentType = "text/css";
        }

        if (path.endsWith(".js")) {
            contentType = "application/javascript";
        }

        exchange.getResponseHeaders()
                .set("Content-Type", contentType);

        byte[] data = readFile(file);

        exchange.sendResponseHeaders(200, data.length);

        OutputStream output = exchange.getResponseBody();

        output.write(data);

        output.close();
    }


    // =========================================
    // ADD STUDENT
    // =========================================

    static void addStudent(HttpExchange exchange) throws IOException {

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {

            exchange.sendResponseHeaders(405, -1);

            return;
        }

        // Read data sent by JavaScript
        InputStream input = exchange.getRequestBody();

        String body = new String(
                input.readAllBytes(),
                StandardCharsets.UTF_8
        );

        // Convert received data into a Map
        Map<String, String> data = parseData(body);


        // Get student details

        int rollNo = Integer.parseInt(
                data.get("rollNo")
        );

        String name = data.get("name");

        int javaMarks = Integer.parseInt(
                data.get("java")
        );

        int pythonMarks = Integer.parseInt(
                data.get("python")
        );

        int mathsMarks = Integer.parseInt(
                data.get("maths")
        );

        int englishMarks = Integer.parseInt(
                data.get("english")
        );

        int dbmsMarks = Integer.parseInt(
                data.get("dbms")
        );


        // Create Student object

        Student student = new Student(
                rollNo,
                name,
                javaMarks,
                pythonMarks,
                mathsMarks,
                englishMarks,
                dbmsMarks
        );


        // Java calculates the result

        student.calculateResult();


        // Store student

        classRoom.addStudent(student);


        // Send result back to JavaScript

        String json =
                "{"
                + "\"rollNo\":" + student.rollNo + ","
                + "\"name\":\"" + escapeJson(student.name) + "\","
                + "\"total\":" + student.total + ","
                + "\"average\":" + student.average + ","
                + "\"grade\":\"" + student.grade + "\""
                + "}";


        exchange.getResponseHeaders()
                .set("Content-Type", "application/json");

        byte[] response =
                json.getBytes(StandardCharsets.UTF_8);

        exchange.sendResponseHeaders(
                200,
                response.length
        );

        OutputStream output =
                exchange.getResponseBody();

        output.write(response);

        output.close();
    }


    // =========================================
    // CONVERT FORM DATA
    // =========================================

    static Map<String, String> parseData(String body) {

        Map<String, String> data =
                new HashMap<>();

        String[] pairs = body.split("&");

        for (String pair : pairs) {

            String[] parts = pair.split("=");

            if (parts.length == 2) {

                String key =
                        URLDecoder.decode(
                                parts[0],
                                StandardCharsets.UTF_8
                        );

                String value =
                        URLDecoder.decode(
                                parts[1],
                                StandardCharsets.UTF_8
                        );

                data.put(key, value);
            }
        }

        return data;
    }


    // =========================================
    // READ FILE
    // =========================================

    static byte[] readFile(File file)
            throws IOException {

        FileInputStream input =
                new FileInputStream(file);

        byte[] data = input.readAllBytes();

        input.close();

        return data;
    }


    // =========================================
    // JSON ESCAPE
    // =========================================

    static String escapeJson(String text) {

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}