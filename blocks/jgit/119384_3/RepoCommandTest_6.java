			try (ObjectReader reader = dest.newObjectReader()) {
				byte[] bytes = reader.open(modId)
						.getCachedBytes(Integer.MAX_VALUE);
				Config base = new Config();
				BlobBasedConfig cfg = new BlobBasedConfig(base
				String subUrl = cfg.getString("submodule"
				assertEquals(subUrl
			}
