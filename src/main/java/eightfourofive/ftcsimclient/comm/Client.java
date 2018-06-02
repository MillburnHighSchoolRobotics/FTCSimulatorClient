package eightfourofive.ftcsimclient.comm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import eightfourofive.ftcsimclient.BuildConfig;

public class Client {



//    private volatile static Client INSTANCE = null;

    public static Byte[] request(int id, int prop) throws MalformedURLException {
        return request(id, prop, null);
    }

    public static Byte[] request(int id, int prop, byte[] data) throws MalformedURLException {
        HttpURLConnection connection = null;
        URL url = new URL("http://localhost:" + BuildConfig.EFOF_PORT + "?id=" + id + "&prop=" + prop);
        try {
            //Create connection
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(data == null ? "GET" : "POST");
            if (data != null) {
                connection.setRequestProperty("Content-Type", "application/octet-stream");
                connection.setRequestProperty("Content-Length", Integer.toString(data.length));
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream());
                wr.write(data);
                wr.close();
                connection.setDoOutput(true);
            }

            connection.setUseCaches(false);

            //Get Response

            InputStream is = connection.getInputStream();
            List<Byte> response = new ArrayList<>();
            byte curr;
            while ((curr = (byte)is.read()) != -1) {
                response.add(curr);
            }
            return response.toArray(new Byte[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}

//    public static Client getInstance() throws IOException {
//        if (INSTANCE == null) {
//            synchronized(Client.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = new Client();
//                }
//            }
//        }
//        return INSTANCE;
//    }

