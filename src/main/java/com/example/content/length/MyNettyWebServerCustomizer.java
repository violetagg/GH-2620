package com.example.content.length;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

@Component
public class MyNettyWebServerCustomizer implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

	@Override
	public void customize(NettyReactiveWebServerFactory factory) {
		factory.addServerCustomizers(httpServer ->
				httpServer.doOnConnection(conn -> conn.addHandlerLast(MyCustomChannelHandler.INSTANCE)));
	}

	static final class MyCustomChannelHandler extends ChannelOutboundHandlerAdapter {

		static final MyCustomChannelHandler INSTANCE = new MyCustomChannelHandler();

		@Override
		public boolean isSharable() {
			return true;
		}

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
			if (msg instanceof FullHttpResponse originalMsg) {
				if (originalMsg.status() == HttpResponseStatus.NO_CONTENT) {
					originalMsg.headers().remove(CONTENT_LENGTH);
				}
				ctx.write(originalMsg, promise);
			}
			else {
				ctx.write(msg, promise);
			}
		}
	}
}