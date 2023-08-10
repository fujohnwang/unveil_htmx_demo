package com.keevol.demo.htmx.pooling

import com.keevol.kate.RouteRegister
import io.vertx.ext.web.Router
import org.apache.commons.lang3.time.DateFormatUtils

import java.util.Date

class PoolingRoutes extends RouteRegister {
  override def apply(router: Router): Unit = {
    router.get("/datetime").handler(ctx => {
      ctx.end(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))
    })

    router.get("/demo/pooling").handler(ctx => {
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
           |	<title>Pooling demo</title>
           |	<script src="https://cdn.tailwindcss.com/"></script>
           |	<script src="https://unpkg.com/htmx.org@1.9.4"></script>
           |	</head>
           |<body>
           |	<main class="container mx-auto p-3 space-y-3">
           |    <span class="text-5xl" hx-get="/datetime" hx-trigger="every 2s"><span>
           |	</main>
           |</body>
           |</html>
           |""".stripMargin)
    })
  }
}