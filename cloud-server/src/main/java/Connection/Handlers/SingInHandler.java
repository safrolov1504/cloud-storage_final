package Connection.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import workingWithMessage.SignIn;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class SingInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        byte[] arr = (byte[]) msg;
        boolean flag = SignIn.checkUser(Arrays.copyOfRange(arr,2,arr.length));

        if(flag){
            //user is ok
            arr = new byte[]{-127, -126};
        } else {
            //user is not ok
            arr = new byte[]{-127, -125};
        }
        ByteBuf buf = ctx.alloc().buffer(arr.length);
        buf.writeBytes(arr);

        ctx.writeAndFlush(buf);

        //ctx.writeAndFlush("ok");
        //ByteBuf buf = ctx.alloc().buffer(arr.length);
        //buf.writeBytes(arr);
        //ctx.writeAndFlush(arr); // от обработчика
        // ctx.channel().writeAndFlush("Java"); // от конца конвеера
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
