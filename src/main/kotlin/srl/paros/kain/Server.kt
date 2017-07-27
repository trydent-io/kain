package srl.paros.kain

import org.jooby.Request
import org.jooby.WebSocket

interface Server<T>

class P2PServer : Server<WebSocket>

class HttpServer : Server<Request>
