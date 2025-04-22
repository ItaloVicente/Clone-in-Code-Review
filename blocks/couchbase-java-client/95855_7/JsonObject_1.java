            if (!provider.checkAlgorithmNameMatch(alg)) {
                throw new CryptoProviderDecryptFailedException("Crypto provider algorithm name mismatch");
            }

            if (!key.contentEquals(provider.getKeyStoreProvider().publicKeyName())) {
                throw new CryptoProviderDecryptFailedException("Public key mismatch");
