package com.keevol.demo.htmx.quickstarter

import com.keevol.demo.htmx.ext.ExtDemoRoutes
import com.keevol.demo.htmx.oob.OobRouteRegister
import com.keevol.demo.htmx.pooling.PoolingRoutes
import com.keevol.demo.htmx.sse.SseHandlers
import com.keevol.demo.htmx.ws.WsRoutes
import com.keevol.kate.{Kate, RouteRegister}
import io.vertx.core.http.HttpHeaders
import io.vertx.ext.web.Router
import kong.unirest.MimeTypes

object Bootstrap {

  val indexPageHtml =
    s"""
       |<!DOCTYPE html>
       |<html>
       |<head>
       |    <meta charset="utf-8"/>
       |    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes"/>
       |    <meta author="fujohnwang"/>
       |    <meta keywords="福强,fujohnwang"/>
       |    <meta description=""/>
       |    <title>HTMX揭秘</title>
       |    <style>
       |        html {
       |            scroll-behavior: smooth;
       |        }
       |    </style>
       |    <script src="https://cdn.tailwindcss.com/"></script>
       |    <script src="https://unpkg.com/htmx.org@1.9.4"></script>
       |</head>
       |<body>
       |<main>
       |    <div class="fixed bottom-0 left-0 w-full top-3 p-3 overflow-y-auto bg-white"
       |    style="max-height: calc(100vh - 6rem);"
       |    >
       |        <div class="flex flex-col gap-2" id="messages">
       |            <!-- 消息展示区 -->
       |            <!-- <div class="bg-green-200 p-2 rounded-md text-right">发送的消息</div>
       |            <div class="bg-blue-200 p-2 rounded-md">回复的消息</div> -->
       |        </div>
       |    </div>
       |
       |    <div class="flex flex-col justify-end items-end
       |    fixed bottom-0 left-0 w-full bg-cyan-50 p-4">
       |        <form class="flex items-center w-full">
       |            <input type="text" placeholder="输入消息..."
       |                   class="flex-grow px-4 py-2 rounded-l-md border border-gray-300 focus:outline-none"
       |                   id="message" name="message">
       |            <button type="submit"
       |                    class="px-4 py-2 bg-blue-500 text-white rounded-r-md hover:bg-blue-600"
       |                    hx-post="/chat"
       |                    hx-target="#messages"
       |                    hx-swap="beforeend"
       |                    >
       |                发送
       |            </button>
       |        </form>
       |    </div>
       |</main>
       |
       |</body>
       |</html>
       |""".stripMargin


  def requestOpenAI(message: String): String = "模拟结果消息： just for test ;)"

  def main(args: Array[String]): Unit = {
    val xchatHandler = new RouteRegister {
      override def apply(router: Router): Unit = {
        // HTTP GET
        router.get("/").handler(ctx => {
          //          ctx.response().putHeader(HttpHeaders.CONTENT_TYPE.toString, MimeTypes.HTML)
          ctx.response().end(indexPageHtml)
        })
        // HTTP POST route for chat request
        router.post("/chat").handler(ctx => {
          val message = ctx.request().getParam("message")
          val responseMessage = requestOpenAI(message)
          //          ctx.response().putHeader(HttpHeaders.CONTENT_TYPE.toString, MimeTypes.HTML)
          val responseContentInHtmlSegment =
            s"""
               |<div class="bg-green-200 p-2 rounded-md text-right">${message}</div>
               |<div class="bg-blue-200 p-2 rounded-md">${responseMessage}</div>
               |""".stripMargin
          ctx.response().end(responseContentInHtmlSegment)
        })
      }
    }
    val oobHandler = new OobRouteRegister()
    val sseHandler = new SseHandlers()
    val poolingHandler = new PoolingRoutes()
    val wsHandler = new WsRoutes()
    val jsonExtHandler = new ExtDemoRoutes()
    val webServer = new Kate(Array(xchatHandler, oobHandler, sseHandler, poolingHandler, wsHandler, jsonExtHandler))
    webServer.start("localhost", 9999)
  }
}