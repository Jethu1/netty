package practice11_TcpMulticast;

/**
 * Created by jet on 2017/6/13.
 */

import practice11_TcpMulticast.ChatRequest;
import practice11_TcpMulticast.ChatResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class SocketHandler extends ChannelInboundHandlerAdapter {

    //channels保存所有链接进来的channel
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    //将链接的channel加到channels中
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        ChatRequest.Command command = (ChatRequest.Command)msg;

        ChatResponse.ChatMessage.Builder build = ChatResponse.ChatMessage.newBuilder();
        build.setChatType(command.getChatType())
                .setContent(command.getContent() + "，已经经过服务器转发");

        ChatResponse.ChatMessage chatMessage = build.build();

        //将消息发送给所有链接的客户端
        channels.writeAndFlush(chatMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
