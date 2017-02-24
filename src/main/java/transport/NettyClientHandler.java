package transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.nio.charset.Charset;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    private ByteBuf buf;
    @Override
    public void channelActive(final ChannelHandlerContext ctx) { // (1)
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println(s);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
    }
}
