	public void setTestUserPublicKey(Path key)
			throws IOException
		this.testKey = AuthorizedKeyEntry.readAuthorizedKeys(key).get(0)
				.resolvePublicKey(PublicKeyEntryResolver.IGNORING);
	}

