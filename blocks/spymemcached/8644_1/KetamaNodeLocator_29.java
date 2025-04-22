    return new KetamaNodeLocator(smn, an, hashAlg, config);
  }

  @Override
  public void updateLocator(List<MemcachedNode> nodes, Config conf) {
    setKetamaNodes(nodes);
  }

  protected TreeMap<Long, MemcachedNode> getKetamaNodes() {
    return ketamaNodes;
  }

  protected void setKetamaNodes(List<MemcachedNode> nodes) {
    TreeMap<Long, MemcachedNode> newNodeMap =
        new TreeMap<Long, MemcachedNode>();
    int numReps = config.getNodeRepetitions();
    for (MemcachedNode node : nodes) {
      if (hashAlg == HashAlgorithm.KETAMA_HASH) {
        for (int i = 0; i < numReps / 4; i++) {
          byte[] digest =
              HashAlgorithm.computeMd5(config.getKeyForNode(node, i));
          for (int h = 0; h < 4; h++) {
            Long k =
                ((long) (digest[3 + h * 4] & 0xFF) << 24)
                    | ((long) (digest[2 + h * 4] & 0xFF) << 16)
                    | ((long) (digest[1 + h * 4] & 0xFF) << 8)
                    | (digest[h * 4] & 0xFF);
            newNodeMap.put(k, node);
            getLogger().debug("Adding node %s in position %d", node, k);
          }

        }
      } else {
        for (int i = 0; i < numReps; i++) {
          newNodeMap.put(hashAlg.hash(config.getKeyForNode(node, i)), node);
        }
      }
