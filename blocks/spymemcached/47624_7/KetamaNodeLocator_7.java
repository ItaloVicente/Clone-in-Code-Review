          if (hashAlg == DefaultHashAlgorithm.KETAMA_HASH) {
              for (int i = 0; i < numReps / 4; i++) {
                  for(long position : ketamaNodePositionsAtIteration(node, i)) {
                    newNodeMap.put(position, node);
                    getLogger().debug("Adding node %s in position %d", node, position);
                  }
              }
          } else {
              for (int i = 0; i < numReps; i++) {
                  newNodeMap.put(hashAlg.hash(config.getKeyForNode(node, i)), node);
              }
          }
