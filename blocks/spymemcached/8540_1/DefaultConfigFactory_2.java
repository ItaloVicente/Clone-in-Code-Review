        String algorithm = jsonObject.getString("hashAlgorithm");
        HashAlgorithm hashAlgorithm = 
          HashAlgorithmRegistry.lookupHashAlgorithm(algorithm);
        if (hashAlgorithm == null){
			throw new IllegalArgumentException(
					"Unhandled algorithm type: " + algorithm);
        }
