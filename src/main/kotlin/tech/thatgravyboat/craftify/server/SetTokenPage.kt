package tech.thatgravyboat.craftify.server

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import tech.thatgravyboat.craftify.services.ServiceHelper

class SetTokenPage : HttpHandler {

    override fun handle(exchange: HttpExchange?) {
        exchange?.let {
            val code = it.requestURI.query
            code?.let { ServiceHelper.loginToSpotify("auth", code) }
            it.sendResponseHeaders(if (code == null) 400 else 200, 0)
            it.close()
            if (code == null) return
            it.httpContext.server.stop(5)
            LoginServer.destroyServer()
        }
    }
}
