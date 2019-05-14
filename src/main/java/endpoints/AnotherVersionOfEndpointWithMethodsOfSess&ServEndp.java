//package endpoints;
//
//
//import javax.imageio.ImageIO;
//import javax.websocket.*;
//import javax.websocket.server.ServerEndpoint;
//import javax.websocket.server.ServerEndpointConfig;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.nio.ByteBuffer;
//import java.util.LinkedList;
//import java.util.List;

//
//
//@ServerEndpoint(value = "/image")
//public class ImageEndpoint {
//    String turnOn = "src/main/webapp/resources/images/bulb2.png";
//    String turnOff = "src/main/webapp/resources/images/bulb.png";
//    String path = null;
//    private Session session = null;
//    private ServerEndpointConfig endpointConfig;
//
//
//    @OnOpen
//    public void OnOpen(EndpointConfig endpointConfig, Session session) {
//        this.endpointConfig = (ServerEndpointConfig) endpointConfig;

//    }
//
//    @OnClose
//    public void OnClose(Session session) {
////        sessionList.remove(session);
//    }
//
//    @OnError
//    public void OnError(Session session, Throwable throwable) {
//        throwable.printStackTrace();
//    }
//
//    @OnMessage
//    public void OnMessage(Session session, String message) {
//
//        System.out.println("Message: " + message);
//        if (message.contains("0")) {
//            session.getOpenSessions().forEach(s -> {
//                if (s != null) {

//                    String cleanNumber = message.substring(0, message.length() - 1);
//                    path = this.endpointConfig.getUserProperties().equals(cleanNumber) ? turnOn : turnOff;
//                    fetchingBulb(s, path);
//                }
//            });
//        } else if (this.endpointConfig.getUserProperties().containsValue(message)) {
//            System.out.println("in list " + this.endpointConfig.getUserProperties().equals(message));
//            this.endpointConfig.getUserProperties().remove(message);
//            session.getOpenSessions().forEach(s -> {
//                if (s != null) {
//                    System.out.println("turn Off");
//                    fetchingBulb(s, turnOff);
//                }
//            });
//        } else if (!this.endpointConfig.getUserProperties().containsValue(message))  {
//            this.endpointConfig.getUserProperties().put(message, message);
//            session.getOpenSessions().forEach(s -> {
//                if (s != null) {
//                    System.out.println("turn ON");
//                    fetchingBulb(s,turnOn);
//
//                }
//            });
//        }
//    }
//
//    private void fetchingBulb(Session s, String path) {
//
//        try {
//            File f = new File(path);
//            BufferedImage bi = ImageIO.read(f);
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            ImageIO.write(bi, "png", out);
//            ByteBuffer byteBuffer = ByteBuffer.wrap(out.toByteArray());
//            s.getBasicRemote().sendBinary(byteBuffer);
//            out.close();
//            byteBuffer.clear();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
