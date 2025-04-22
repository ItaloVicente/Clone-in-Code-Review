      if (isWeightedKetama) {

          int thisWeight = weights.get(node.getSocketAddress());
          float percent = (float)thisWeight / (float)totalWeight;
          int pointerPerServer = (int)((Math.floor((float)(percent * (float)config.getNodeRepetitions() / 4 * (float)nodeCount + 0.0000000001))) * 4);
          for (int i = 0; i < pointerPerServer / 4; i++) {
              for(long position : ketamaNodePositionsAtIteration(node, i)) {
                  newNodeMap.put(position, node);
                  getLogger().debug("Adding node %s with weight %s in position %d", node, thisWeight, position);
              }
