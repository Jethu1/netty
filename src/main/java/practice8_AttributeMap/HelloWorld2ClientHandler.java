package practice8_AttributeMap;

/**
 * Created by jet on 2017/6/8.
 */

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;

import java.util.Date;

public class HelloWorld2ClientHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        Attribute<NettyChannel> attr = ctx.attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        Attribute<NettyChannel> attr = ctx.channel().attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        NettyChannel nChannel = attr.get();
        if (nChannel == null) {
            NettyChannel newNChannel = new NettyChannel("HelloWorld2Client", new Date());
            nChannel = attr.setIfAbsent(newNChannel);
        } else {
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(nChannel.getName() + "=======" + nChannel.getCreateDate());
        }
        System.out.println("HelloWorldC2ientHandler Active");
        ctx.fireChannelActive();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        Attribute<NettyChannel> attr = ctx.attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        Attribute<NettyChannel> attr = ctx.channel().attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        NettyChannel nChannel = attr.get();
        if (nChannel == null) {
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
            nChannel = attr.setIfAbsent(newNChannel);
        } else {
            System.out.println("channelRead attributeMap 中是有值的");
            System.out.println(nChannel.getName() + "=======" + nChannel.getCreateDate());
        }
        System.out.println("HelloWorldClientHandler read Message:" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
