
	private static class LfsRequest {
		String operation;

		List<LfsObject> objects;
	}

	private static Gson createGson() {
		GsonBuilder gb = new GsonBuilder()
				.setFieldNamingPolicy(
						FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.setPrettyPrinting().disableHtmlEscaping();
		return gb.create();
	}
