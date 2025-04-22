      if (isWeightedKetama) {

          int thisWeight = weights.get(node.getSocketAddress());
          float percent = (float)thisWeight / (float)this.totalWeight;
          int pointerPerServer = (int)((Math.floor((float)(percent * (float)config.getNodeRepetitions() / 4 * (float)nodeCount + 0.0000000001))) * 4);
          for (int i = 0; i < pointerPerServer / 4; i++) {
              byte[] digest = DefaultHashAlgorithm.computeMd5(config.getKeyForNode(node, i));
              for (int h = 0; h < 4; h++) {
                  Long k = ((long) (digest[3 + h * 4] & 0xFF) << 24)
                      | ((long) (digest[2 + h * 4] & 0xFF) << 16)
                      | ((long) (digest[1 + h * 4] & 0xFF) << 8)
                      | (digest[h * 4] & 0xFF);
                  newNodeMap.put(k, node);
                  getLogger().debug("Adding node %s with weight %s in position %d", node, thisWeight, k);
              }
