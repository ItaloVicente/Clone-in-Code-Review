	void setSSLSocketFactory(@NonNull SSLConnectionSocketFactory factory
			boolean isDefault) {
		socketFactory = factory;
		usePooling = isDefault;
	}

