        return new KetamaNodeLocator(nodes, getHashAlg(),
                getKetamaNodeKeyFormat(), getWeights());
    }

  public KetamaNodeKeyFormat getKetamaNodeKeyFormat() {
      return ketamaNodeKeyFormat;
  }

  public Map<InetSocketAddress, Integer> getWeights() {
      return weights;
