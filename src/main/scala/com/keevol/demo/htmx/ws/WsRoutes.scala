package com.keevol.demo.htmx.ws

import com.keevol.kate.RouteRegister
import io.vertx.core.http.ServerWebSocket
import io.vertx.ext.web.Router
import org.apache.commons.lang3.exception.ExceptionUtils
import org.apache.commons.lang3.time.DateFormatUtils
import org.slf4j.LoggerFactory

import java.util.Date
import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class WsRoutes extends RouteRegister {
  private val logger = LoggerFactory.getLogger(classOf[WsRoutes])

  override def apply(router: Router): Unit = {
    router.get("/demo/ws").handler(ctx => {
      ctx.response().end(
        s"""
           |<!DOCTYPE html>
           |<html>
           |	<head>
           |	  <meta charset="utf-8" />
           |  	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes" />
           |  	<meta author="fujohnwang" />
           |  	<meta keywords="福强,fujohnwang" />
           |  	<meta description="" />
           |	  <title>Pooling demo</title>
           |	  <script src="https://cdn.tailwindcss.com/"></script>
           |	  <script src="https://unpkg.com/htmx.org@1.9.4"></script>
           |	</head>
           |<body>
           |	<main class="container mx-auto p-3 space-y-3">
           |    <div hx-ws="connect:/xchat">
           |    <div id="chats">
           |    </div>
           |    <form hx-ws="send" hx-on:submit="htmx.find('#chat').value='';">
           |        <input id="chat" name="chat" class="border w-full">
           |    </form>
           |  </div>
           |	</main>
           |</body>
           |</html>
           |""".stripMargin)
    })

    router.route("/xchat").handler(ctx => {
      ctx.request().toWebSocket(ar => {
        if (ar.succeeded()) {
          val serverWebSocket = ar.result()
          serverWebSocket.textMessageHandler(str => {
            println(s"行啦，你的Ws消息收到啦：$str")
          })
          serverWebSocket.closeHandler(_ => {
            println("do clean up if any")
          })

          Future {
            (0 to 5).foreach(_ => {
              TimeUnit.SECONDS.sleep(5)
              serverWebSocket.writeTextMessage(
                s"""
                   |<div id="chats">
                   |      <div class="text-6xl m-6 p-6 bg-amber-300 text-center">
                   |        随便写点儿什么吧！ @ ${DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss")}
                   |      </div>
                   |    </div>
                   |""".stripMargin)
            })
          }
        } else {
          logger.warn(ExceptionUtils.getStackTrace(ar.cause()))
        }
      })
    })

  }
}