package Connection.Handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import workingWithMessage.AddFile;

// Идет после FirstHandler в конвеере
public class CommandHandler extends ChannelInboundHandlerAdapter {
    private AddFile addFile;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Мы точно знаем, что предыдущий обработчик отдает нам массив байт
        byte[] arr = (byte[])msg;
        // Каждый элемент массива увеличиваем на 1

        //читаем команду
        switch (arr[0]){
            case -127:
                switch (arr[1]){
                    case -126:
                        ctx.fireChannelRead(arr);
                        break;
                }
                break;

            case -124:
                switch (arr[1]){
                    case -123:
                        addFile = new AddFile();
                        addFile.startWorkFile(arr);
                        break;
                    case -122:
                        addFile.continueWorkFile(arr);
                        break;
                    case -121:
                        sendBack(ctx,addFile.endWorkFile(arr));
                        break;
                }
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + arr[0]);
        }
        //System.out.println("Второй шаг: " + Arrays.toString(arr));
        // Кидаем полученный массив дальше по конвееру
        //ctx.fireChannelRead(arr);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public void sendBack(ChannelHandlerContext ctx, byte[] arr){
        ByteBuf buf = ctx.alloc().buffer(arr.length);
        buf.writeBytes(arr);

        ctx.writeAndFlush(buf);
    }
}
