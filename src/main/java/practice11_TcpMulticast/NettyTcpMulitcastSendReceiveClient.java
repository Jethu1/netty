package practice11_TcpMulticast;

/**
 * Created by jet on 2017/6/13.
 */
import java.io.DataInputStream;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.net.Socket;

        import practice11_TcpMulticast.ChatRequest;
        import practice11_TcpMulticast.ChatResponse;


public class NettyTcpMulitcastSendReceiveClient {

    public static void main(String[] args) throws IOException {

        Socket socket = null;
        DataOutputStream dataOut = null;
        DataInputStream dataIn = null;

        try {

            socket = new Socket("localhost", 8080);

            dataOut = new DataOutputStream(socket.getOutputStream());
            dataIn = new DataInputStream(socket.getInputStream());

            ChatRequest.Command.Builder requestBuiler = ChatRequest.Command.newBuilder();
            requestBuiler.setChatType(0)
                    .setContent("我是发送方");
            ChatRequest.Command Command = requestBuiler.build();

            byte[] dataOutBytes = Command.toByteArray();

            //先发送header
            dataOut.writeInt(dataOutBytes.length);

            //再发送body
            dataOut.write(dataOutBytes);
            dataOut.flush();


            int bodyLength = dataIn.readInt();
            byte[] bodyBytes = new byte[bodyLength];
            dataIn.read(bodyBytes);

            //将字节流转成ChatResponse.ChatMessage类型
            ChatResponse.ChatMessage chatResponse = ChatResponse.ChatMessage.parseFrom(bodyBytes);

            System.out.println("chatType: " + chatResponse.getChatType());
            System.out.println("chatContent: " + chatResponse.getContent());

        } finally {
            // 关闭连接
            dataIn.close();
            dataOut.close();
            socket.close();
        }
    }
}
