package srl.paros.kain

import com.google.common.jimfs.Configuration.unix
import com.google.common.jimfs.Jimfs.newFileSystem
import java.nio.file.FileSystem
import java.nio.file.Files.createDirectory
import java.nio.file.Files.exists
import java.nio.file.Path
import java.util.Collections.emptyList
import java.util.Collections.synchronizedList

interface Blockchain : Iterable<Block> {
  fun add(data: ByteArray): Blockchain
  fun last(): Block

  fun next(data: ByteArray) = NaiveBlock(
    previous = last().hash(),
    data = data
  )
}

private class InMemoryBlockchain(private val chain: MutableList<Block>) : Blockchain {
  override fun add(data: ByteArray): Blockchain {
    chain.add(next(data))
    return this
  }

  override fun last() = chain.last()

  override fun iterator() = chain.iterator()
}

private class FileSystemBlockchain(private val fs: FileSystem) : Blockchain {
  private fun file(): Path {
    val folder = fs.getPath("/blockchain")

    if (!exists(folder)) createDirectory(folder)

    return folder.resolve("blockchain.data")
  }

  private fun get(): Blockchain {
    return this
  }

  override fun add(data: ByteArray): Blockchain {

    return this
  }

  override fun last(): Block {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun iterator(): Iterator<Block> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}

fun syncBlockchain(): Blockchain = InMemoryBlockchain(synchronizedList(emptyList()))
fun fileSystemBlockchain(fs: FileSystem): Blockchain = FileSystemBlockchain(fs)
fun fakeFileSystemBlockchain(): Blockchain = FileSystemBlockchain(newFileSystem(unix()))
