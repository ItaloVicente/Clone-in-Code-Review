			return "FileStoreAttributeCache["
					+ attributeCache.keySet().stream().map(key -> {
						FileStoreAttributeCache c = attributeCache.get(key);
						Long tr = c.fsTimestampResolution.toNanos() / 1000;
						Long mri = c.minimalRacyInterval.toNanos() / 1000;
						return String.format(
								"FileStore[%s]: fsTimestampResolution=%
										+ "minimalRacyInterval=%
								key
					}).collect(Collectors.joining("
