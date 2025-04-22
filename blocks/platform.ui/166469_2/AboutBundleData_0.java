	public SignedContent getSignedContent() throws IllegalStateException {
		final SignedContent[] result = new SignedContent[1];
		ServiceCaller.callOnce(getClass(), SignedContentFactory.class, (contentFactory) -> {
			try {
				result[0] = contentFactory.getSignedContent(bundle);
			} catch (IOException | GeneralSecurityException e) {
				throw new IllegalStateException(e);
			}
		});
		return result[0];
	}
