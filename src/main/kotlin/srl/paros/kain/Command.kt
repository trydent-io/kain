package srl.paros.kain

import io.reactivex.Observable
import io.reactivex.Observable.*
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.Subject
import org.jooby.WebSocket
import java.util.function.Function
import java.util.function.Supplier

interface Command<T> : Function<T, Boolean>
interface WebSocketCommand : Command<WebSocket>

interface SendBlockchain : WebSocketCommand
interface SendBlock : WebSocketCommand
interface SendPeers : WebSocketCommand

interface WebSocketCommands : Iterable<WebSocketCommand>

private class DefaultSendBlockchain(val blockchain: Blockchain) : SendBlockchain {
  override fun apply(ws: WebSocket): Boolean {
    ws.broadcast(blockchain)
    return true
  }
}

private class DefaultSendBlock(val block: Block) : WebSocketCommand {
  override fun apply(ws: WebSocket): Boolean {
    ws.send(block)
    return true
  }
}

private class DefaultSendBlockchainCommands(val commands: Array<SendBlockchain>) : Observable<SendBlockchain>() {

  override fun subscribeActual(observer: Observer<in SendBlockchain>?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}

interface Events : Observer<Command> {
  fun push(command: Command)
}

private class ReactiveEvents(private val subject: Subject<Command>) : Events {


  override fun push(command: Command) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onComplete() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onNext(t: Command) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onSubscribe(d: Disposable) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onError(e: Throwable) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
