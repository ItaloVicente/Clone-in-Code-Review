		static boolean isAlgorithmAllowed(Properties props) {
			try {
				WalkEncryption.instance(props);
				return true;
			} catch (GeneralSecurityException e) {
				return false;
			}
		}

