        return new KetamaNodeLocator(nodes, getHashAlg(),
                getKetamaNodeKeyFormat(), getWeights());
    }

  public KetamaNodeKeyFormatter.Format getKetamaNodeKeyFormat() {
      return ketamaNodeKeyFormat;
  }

  public Map<InetSocketAddress, Integer> getWeights() {
      return weights;
