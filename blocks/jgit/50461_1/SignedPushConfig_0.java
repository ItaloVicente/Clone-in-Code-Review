
	public void setNonceGenerator(NonceGenerator generator) {
		nonceGenerator = generator;
	}

	public NonceGenerator getNonceGenerator() {
		if (nonceGenerator != null) {
			return nonceGenerator;
		} else if (certNonceSeed != null) {
			return new HMACSHA1NonceGenerator(certNonceSeed);
		}
		return null;
	}
