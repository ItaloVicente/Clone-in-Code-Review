	public static boolean isStaleFileHandleInCausalChain(Throwable throwable) {
		while (throwable != null) {
			if (throwable instanceof IOException
					&& isStaleFileHandle((IOException) throwable)) {
				return true;
			}
			throwable = throwable.getCause();
		}
		return false;
	}

