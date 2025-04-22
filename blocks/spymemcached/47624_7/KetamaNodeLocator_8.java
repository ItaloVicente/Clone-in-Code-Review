
  private List<Long> ketamaNodePositionsAtIteration(MemcachedNode node, int iteration) {
      List<Long> positions = new ArrayList<Long>();
      byte[] digest = DefaultHashAlgorithm.computeMd5(config.getKeyForNode(node, iteration));
      for (int h = 0; h < 4; h++) {
          Long k = ((long) (digest[3 + h * 4] & 0xFF) << 24)
              | ((long) (digest[2 + h * 4] & 0xFF) << 16)
              | ((long) (digest[1 + h * 4] & 0xFF) << 8)
              | (digest[h * 4] & 0xFF);
          positions.add(k);
      }
      return positions;
  }
