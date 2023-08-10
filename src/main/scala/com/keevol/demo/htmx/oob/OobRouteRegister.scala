package com.keevol.demo.htmx.oob

import com.keevol.kate.RouteRegister
import io.vertx.ext.web.Router

/**
 * Just demo code, the result HTML is not in formal format.
 */
class OobRouteRegister extends RouteRegister {
  override def apply(router: Router): Unit = {

    router.get("/sessionHistory").handler(ctx => {
      val sessionId = ctx.request().getParam("session_id")
      val sessionContent = getSessionContent(sessionId)

      if (ctx.request().headers().contains("HX-History-Restore-Request")) {
        ctx.response().end(
          s"""
             |<div id="messages" hx-swap-oob="true">
             |    $sessionContent
             |</div>
             |<html>
             |    ...
             |    <div id="messages">
             |        对应会话内容
             |    </div>
             |    ...
             |</html>
             |""".stripMargin)
      } else {
        ctx.response().end(
          s"""
             |<div id="messages">
             |   $sessionContent
             |</div>
             |""".stripMargin)
      }
    })


  }

  private def getSessionContent(sessionId: String): String = {
    s"""
       | Mock data
       |""".stripMargin
  }
}