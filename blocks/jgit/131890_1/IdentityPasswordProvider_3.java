	protected ResourceDecodeResult handleDecodeAttemptResult(String resourceKey
			State state
			throws IOException
		if (err == null) {
			return null;
		} else if (err instanceof GeneralSecurityException) {
			throw new InvalidKeyException(
					format(SshdText.get().identityFileCannotDecrypt
							resourceKey)
					err);
		} else {
			if (state == null || password == null
					|| state.getCount() >= attempts) {
				return ResourceDecodeResult.TERMINATE;
			}
			return ResourceDecodeResult.RETRY;
		}
	}

	@Override
	public ResourceDecodeResult handleDecodeAttemptResult(String resourceKey
			String password
			throws IOException
		ResourceDecodeResult result = null;
		State state = null;
		try {
			state = current.get(resourceKey);
			result = handleDecodeAttemptResult(resourceKey
					state == null ? null : state.getPassword()
		} finally {
			if (state != null) {
				state.setPassword(null);
			}
			if (result != ResourceDecodeResult.RETRY) {
				current.remove(resourceKey);
			}
		}
		return result;
	}
