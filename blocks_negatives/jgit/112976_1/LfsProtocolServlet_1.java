		gson.toJson(new Error(message), writer);
	}

	private Gson createGson() {
		return new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.disableHtmlEscaping()
				.create();
