package controller;

import Util.SessionUtil;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@WebServlet("/login")
public class LoginApi extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        SessionUtil.getInstance().putValue(req, "ADMIN", "NAM_ADMIN");

        new ObjectMapper().writeValue(resp.getOutputStream(),
                SessionUtil.getInstance().getValue(req, "ADMIN"));



        String fileName = "file.txt";
        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource(fileName).getFile());

//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write("Siếu qúa \n ko hả".getBytes(StandardCharsets.UTF_8));
//        fos.close();


        List<String> stringReads = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringReads) {
            stringBuilder.append(s);
        }
        System.out.println(stringBuilder.toString());

    }
}
