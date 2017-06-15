package practice11_TcpMulticast;

/**
 * Created by jet on 2017/6/13.
 */
import practice11_TcpMulticast.ChatRequest;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;


public class NettyTcpMulticastServer {

    public static void main(String[] args) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);

        try {
            ServerBootstrap bs = new ServerBootstrap();
            bs.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {

                            ProtobufDecoder protobufDecoder = new ProtobufDecoder(ChatRequest.Command.getDefaultInstance());

                            //Decoder
                            ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(2 * 1024 * 1024, 0, 4, 0, 4));
                            ch.pipeline().addLast("protobufDecoder", protobufDecoder);

                            //Encoder
                            ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(4));
                            ch.pipeline().addLast("protobufEncoder", new ProtobufEncoder());


                            //handler
                            ch.pipeline().addLast(new SocketHandler());
                        }
                    })

                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //Bind and start to accept incoming connections
            ChannelFuture cf = bs.bind(8080).sync();

            cf.channel().closeFuture().sync();
        } finally {
            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
