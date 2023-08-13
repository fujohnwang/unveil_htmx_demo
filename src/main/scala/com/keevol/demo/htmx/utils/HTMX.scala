package com.keevol.demo.htmx.utils

import io.vertx.ext.web.RoutingContext

/**
 * This is just for demo, not a completed utility class.
 */
object HTMX {

  /* Define a bulk of predicates in the following */
  def isHtmxRequest(ctx: RoutingContext): Boolean = ctx.request().headers().contains("HX-Request")
  // more definitions expected...

  /* Define a bulk of value retrievers in the following */
  def getHtmxPrompt(ctx: RoutingContext): Option[String] = {
    if (ctx.request().headers().contains("HX-Prompt")) {
      Some(ctx.request().headers().get("HX-Prompt"))
    } else {
      None
    }
  }
  // more definitions expected...
}