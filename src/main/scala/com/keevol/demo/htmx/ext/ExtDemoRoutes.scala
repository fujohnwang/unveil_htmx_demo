package com.keevol.demo.htmx.ext

import com.keevol.kate.RouteRegister
import io.vertx.core.http.HttpHeaders
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.Router

class ExtDemoRoutes extends RouteRegister {
  override def apply(router: Router): Unit = {
    router.get("/demo/ext/jsonenc").handler(ctx => {
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
           |	<title>json-enc extension demo</title>
           |	<script src="https://cdn.tailwindcss.com/"></script>
           |	<script src="https://unpkg.com/htmx.org@1.9.4"></script>
           |	<script src="https://unpkg.com/htmx.org/dist/ext/json-enc.js"></script>
           | <script src="https://unpkg.com/htmx.org/dist/ext/client-side-templates.js"></script>
           | <script src="https://unpkg.com/mustache@latest"></script>
           |	</head>
           |<body>
           |	<main class="container mx-auto p-3 space-y-3">
           |		<div hx-ext='json-enc,client-side-templates' mustache-template="mt">
           |			<form>
           |				<input id="name" name="name" class="border p-2"/>
           |				<input id="email" name="email" class="border p-2"/>
           |				<button hx-post='/ext/jsonenc' hx-target="#response"
           |          class="border text-white bg-blue-600 p-2">
           |            发送
           |        </button>
           |        <div id="response"></div>
           |
           |        <!-- with client side templates extension-->
           |        <template id="mt">
           |          <h1 class="title-font text-3xl m-6 font-medium text-gray-900">
           |            {{name}}
           |          </h1>
           |          <p class="leading-relaxed text-blue-600 text-9xl">
           |            {{email}}
           |          </p>
           |        </template>
           |			</form>
           |		</div>
           |	</main>
           |
           |</body>
           |</html>
           |
           |""".stripMargin)
    })
    router.post("/ext/jsonenc").handler(ctx => {
      val json: JsonObject = ctx.body().asJsonObject()
      ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json")
      // echo back
      ctx.response().end(json.encode())
    })

  }
}