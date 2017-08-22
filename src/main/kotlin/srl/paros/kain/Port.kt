package srl.paros.kain

interface Port {
  val value: Int
}

data class HttpPort(override val value: Int) : Port
interface HttpsPort : Port
interface ClusterPort : Port
