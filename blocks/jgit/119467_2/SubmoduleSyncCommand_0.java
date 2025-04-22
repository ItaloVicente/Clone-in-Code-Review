				try (Repository subRepo = generator.getRepository()) {
					if (subRepo == null) {
						continue;
					}

					StoredConfig subConfig;
					String branch;
