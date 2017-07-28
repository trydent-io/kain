package srl.paros.kain

import org.jooby.WebSocket
import java.util.function.Function
import java.util.function.Supplier

val retrieveBlockchain = { ws:WebSocket, bc:Blockchain -> ws.send(bc) }
