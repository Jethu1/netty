package practice11_TcpMulticast;

/**
 * Created by jet on 2017/6/13.
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import practice11_TcpMulticast.ChatResponse;


public class NettyTcpMulitcastReceiveClient {

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        DataInputStream dataIn = null;

        try {

            socket = new Socket("localhost", 8080);
            dataIn = new DataInputStream(socket.getInputStream());

            int bodyLength = dataIn.readInt();
            byte[] bodyBytes = new byte[bodyLength];
            dataIn.read(bodyBytes);

            //将字节流转成ChatResponse.ChatMessage类型
            ChatResponse.ChatMessage chatResponse = ChatResponse.ChatMessage.parseFrom(bodyBytes);

            int chatType = chatResponse.getChatType();
            String chatContent = chatResponse.getContent();

            System.out.println("chatType: " + chatType);
            System.out.println("chatContent: " + chatContent);

        } finally {
            // 关闭连接
            dataIn.close();
            socket.close();
        }
    }

}