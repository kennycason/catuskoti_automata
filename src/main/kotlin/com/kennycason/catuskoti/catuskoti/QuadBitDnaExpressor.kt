package com.kennycason.catuskoti.catuskoti

/**
 * dna format
 * [
 *   [x,y,z],                 // input
 *   node = [and|or|xor|not,  // node type
 *      x,y,z,                // node position
 *      [L,node|              // recursive nodes connections
 *       R,node|
 *       U,node|
 *       D,node|
 *       F,node|
 *       B,node
 *      ]*
 *   ]
 * ]
 *
 *
 *
 *
*/
class QuadBitDnaExpressor {

    fun express(dna: String) : QuadBitNetwork {
        val genes = dna.split(",")
        val network: MutableMap<Int, MutableMap<Int, MutableMap<Int, Node>>> = mutableMapOf()


        return QuadBitNetwork(network)
    }

}


