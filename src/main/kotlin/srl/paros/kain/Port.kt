package srl.paros.kain

interface Port {
  val value: Int
}

interface HttpPort : Port
interface HttpsPort : Port
interface ClusterPort : Port
