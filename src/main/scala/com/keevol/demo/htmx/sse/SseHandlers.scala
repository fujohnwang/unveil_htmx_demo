package com.keevol.demo.htmx.sse

import com.keevol.kate.RouteRegister
import io.vertx.ext.web.Router

import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SseHandlers extends RouteRegister {
  override def apply(router: Router): Unit = {
    router.get("/demo/sse").handler(ctx => {
      ctx.response().end(
        s"""
           |<!DOCTYPE html>
           |<html>
           |	<head>
           |	<meta charset="utf-8" />
           |  	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes" />
           |  	<meta author="fujohnwang" />
           |  	<meta keywords="福强,fujohnwang" />
           |  	<meta description="" />
           |	<title>SSE demo</title>
           |	<script src="https://cdn.tailwindcss.com/"></script>
           |	<script src="https://unpkg.com/htmx.org@1.9.4"></script>
           |	</head>
           |<body>
           |	<main class="container mx-auto p-3 space-y-3">
           |
           |		<div id="sse"
           |      hx-sse="connect:/sse" hx-trigger="load delay:3s"
           |      hx-on:htmx:sse-error="this.remove();">
           |			<div hx-sse="swap:e1">
           |			  placeholder for event 1
           |			</div>
           |			<div hx-sse="swap:e2">
           |			  placeholder for event 2
           |			</div>
           |		</div>
           |	</main>
           | <script>
           |  htmx.logAll();
           | </script>
           |</body>
           |</html>
           |
           |""".stripMargin)
    })
    router.route("/sse").handler(ctx => Future {
      val response = ctx.response()
      // set headers
      response.headers().add("Content-Type", "text/event-stream;charset=UTF-8")
      // OR response.setChunked(true)
      response.headers().add("Transfer-Encoding", "chunked")
      response.headers().add("Connection", "keep-alive")
      response.headers().add("Cache-Control", "no-cache")
      response.headers().add("Access-Control-Allow-Origin", "*")

      response.write(
        s"""
           |event: e1
           |data: <span>data for event 11111111</span>
           |\n\n
           |""".stripMargin)

      TimeUnit.SECONDS.sleep(3) // just for demo, don't take it seriously

      response.write(
        s"""
           |event: e2
           |data: <span>data for event 22222222</span>
           |\n\n
           |""".stripMargin)

      response.end("\n\n")

      // stop observing it the pipe is broken
      response.closeHandler { ctx =>
        println("clean up if necessary")
      }
    })

  }


}

object SseHandlers {
  def main(args: Array[String]): Unit = {
    print(
      s"""
         |event: e1
         |data: <span>data for event 11111111</span>
         |\n\n
         |""".stripMargin)
    println("-----")
  }
}

