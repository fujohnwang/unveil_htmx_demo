package com.keevol.demo.htmx.indicator

import com.keevol.kate.RouteRegister
import io.vertx.ext.web.Router

import java.util.concurrent.TimeUnit
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class IndicatorRoutes extends RouteRegister {
  override def apply(router: Router): Unit = {
    router.get("/demo/indicator").handler(ctx => {
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
           |	<title>Indicator Demo</title>
           |	<style>
           |	html{
           |	   scroll-behavior: smooth;
           |	}
           |
           |	.x-indicator{
           |    display:none;
           |	}
           |	.htmx-request .x-indicator{
           |	    display:inline;
           |	}
           |	.htmx-request.x-indicator{
           |	    display:inline;
           |	}
           |	</style>
           |	<script src="https://cdn.tailwindcss.com/"></script>
           |	<script src="https://unpkg.com/htmx.org@1.9.4"></script>
           |	</head>
           |<body>
           |	<main class="container mx-auto p-3 space-y-3">
           |  <form>
           |		<svg id="indicator" class="x-indicator" width="55" height="80" viewBox="0 0 55 80" xmlns="http://www.w3.org/2000/svg" fill="#3383F8">
           |    <g transform="matrix(1 0 0 -1 0 80)">
           |        <rect width="10" height="20" rx="3">
           |            <animate attributeName="height"
           |                 begin="0s" dur="4.3s"
           |                 values="20;45;57;80;64;32;66;45;64;23;66;13;64;56;34;34;2;23;76;79;20" calcMode="linear"
           |                 repeatCount="indefinite" />
           |        </rect>
           |        <rect x="15" width="10" height="80" rx="3">
           |            <animate attributeName="height"
           |                 begin="0s" dur="2s"
           |                 values="80;55;33;5;75;23;73;33;12;14;60;80" calcMode="linear"
           |                 repeatCount="indefinite" />
           |        </rect>
           |        <rect x="30" width="10" height="50" rx="3">
           |            <animate attributeName="height"
           |                 begin="0s" dur="1.4s"
           |                 values="50;34;78;23;56;23;34;76;80;54;21;50" calcMode="linear"
           |                 repeatCount="indefinite" />
           |        </rect>
           |        <rect x="45" width="10" height="30" rx="3">
           |            <animate attributeName="height"
           |                 begin="0s" dur="2s"
           |                 values="30;45;13;80;56;72;45;76;34;23;67;30" calcMode="linear"
           |                 repeatCount="indefinite" />
           |        </rect>
           |    </g>
           |</svg>
           |		<input id="test" name="test" class="border"/>
           |		<button hx-post="/indicator" hx-swap="none" hx-indicator="#indicator">发送</button>
           |  </form>
           |	</main>
           |
           |</body>
           |</html>
           |""".stripMargin)
    })

    router.post("/indicator").handler(ctx => {
      println(s"'test' param doesn't matter in fact: ${ctx.request().getParam("test")}")
      Future {
        // delay 3 sec to mimic request latency
        TimeUnit.SECONDS.sleep(3)
        ctx.response().end()
      }
    })

  }
}