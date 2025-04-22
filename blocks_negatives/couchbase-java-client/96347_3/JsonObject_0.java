            if (provider == null) {
                throw new CryptoProviderNameNotFoundException();
            }

            if (!provider.checkAlgorithmNameMatch(alg)) {
                throw new CryptoProviderDecryptFailedException("Crypto provider algorithm name mismatch");
            }
