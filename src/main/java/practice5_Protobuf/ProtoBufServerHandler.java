package practice5_Protobuf;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import practice5_Protobuf.RichManProto.RichMan.Car;
import java.util.List;

public class ProtoBufServerHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RichManProto.RichMan req = (RichManProto.RichMan) msg;
        System.out.println(req.getName()+"他有"+req.getCarsCount()+"量车");
        List<Car> lists = req.getCarsList();
        if(null != lists) {
            
            for(Car car : lists){
                System.out.println(car.getName());
            }
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close(); 
    }

}
