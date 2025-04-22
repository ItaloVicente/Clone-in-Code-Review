		/**
		 * Verify if any security provider published the algorithm.
		 *
		 * @param algorithm
		 * @return result
		 */
		static boolean isAlgorithmPresent(String algorithm) {
			Set<String> cipherSet = Security.getAlgorithms("Cipher");
			for (String source : cipherSet) {
				String target = algorithm.toUpperCase();
				if (source.equalsIgnoreCase(target)) {
					return true;
				}
			}
			return false;
		}

