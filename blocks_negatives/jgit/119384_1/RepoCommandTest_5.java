		try (ObjectReader reader = dest.newObjectReader()) {
			byte[] bytes = reader.open(modId).getCachedBytes(Integer.MAX_VALUE);
			Config base = new Config();
			BlobBasedConfig cfg = new BlobBasedConfig(base, bytes);
			String subUrl = cfg.getString("submodule", "base", "url");
			assertEquals(subUrl, "../base");
