    HashAlgorithm hashAlgorithm =
        lookupHashAlgorithm(jsonObject.getString("hashAlgorithm"));
    int replicasCount = jsonObject.getInt("numReplicas");
    if (replicasCount > VBucket.MAX_REPLICAS) {
      throw new ConfigParsingException("Expected number <= "
          + VBucket.MAX_REPLICAS + " for replicas.");
