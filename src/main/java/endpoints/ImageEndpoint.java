package endpoints;


import javax.imageio.ImageIO;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@ServerEndpoint(value = "/image")
public class ImageEndpoint {
    private String turnOn = "src/main/webapp/resources/images/bulb2.png";
    private String turnOff = "src/main/webapp/resources/images/bulb.png";
    private String path = null;
    private Session session = null;
    private static List<Session> sessionList = new CopyOnWriteArrayList<>();// one per context
    private static List<String> bulbMatcher = new CopyOnWriteArrayList<>();


    @OnOpen
    public void OnOpen(Session session) {
        this.session = session;
        sessionList.add(session);
    }

    @OnClose
    public void OnClose(Session session) {
        sessionList.remove(session);
    }

    @OnError
    public void OnError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
//ok
   
    @OnMessage
    public void OnMessage(Session session, String message) {
        if (message.contains("0")) {
            sessionList.forEach(s -> {
                if (s != null) {
                    String cleanNumber = message.substring(0, message.length() - 1);
                    path = bulbMatcher.contains(cleanNumber) ? turnOn : turnOff;
                    fetchingBulb(s, path);
                }
            });
        } else if (bulbMatcher.contains(message)) {
            bulbMatcher.remove(message);
            sessionList.forEach(s -> {
                if (s != null) {

                    fetchingBulb(s, turnOff);
                }
            });
        } else if (!bulbMatcher.contains(message)) {
            bulbMatcher.add(message);
            sessionList.forEach(s -> {
                if (s != null) {
                    fetchingBulb(s, turnOn);

                }
            });
        }
    }

    private void fetchingBulb(Session s, String path) {

        try {
            File f = new File(path);
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(bi, "png", out);
            ByteBuffer byteBuffer = ByteBuffer.wrap(out.toByteArray());
            s.getBasicRemote().sendBinary(byteBuffer);
            out.close();
            byteBuffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
