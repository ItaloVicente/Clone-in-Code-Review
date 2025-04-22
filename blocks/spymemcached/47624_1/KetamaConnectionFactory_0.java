      this(qLen, bufSize, opQueueMaxBlockTime,
              DefaultHashAlgorithm.KETAMA_HASH,
              KetamaNodeKeyFormat.SPYMEMCACHED,
              new HashMap<InetSocketAddress, Integer>());
  }

  public KetamaConnectionFactory(
          int qLen,
          int bufSize,
          long opQueueMaxBlockTime,
          HashAlgorithm hash,
          KetamaNodeKeyFormat nodeKeyFormat,
          Map<InetSocketAddress, Integer> weights) {
      super(qLen, bufSize, hash);
      this.ketamaNodeKeyFormat = nodeKeyFormat;
      this.weights = weights;
