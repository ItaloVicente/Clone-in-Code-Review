        return new KetamaNodeLocator(nodes, getHashAlg(),
                getKetamaNodeKeyFormat(), getWeights());
    }

  public KetamaNodeKeyFormat.Format getKetamaNodeKeyFormat() {
      return ketamaNodeKeyFormat;
  }

  public Map<InetSocketAddress, Integer> getWeights() {
      return weights;
