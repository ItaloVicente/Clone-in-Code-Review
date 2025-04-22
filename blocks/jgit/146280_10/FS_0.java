								if (useConfig) {
									cache = readFromConfig(s);
								}
								if (cache.isPresent()) {
									return cache;
								}

