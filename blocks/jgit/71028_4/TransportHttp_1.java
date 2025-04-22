				final HttpConnection conn;
				try {
					conn = httpOpen(u);
				} catch (IOException e) {
					if (Type.NONE != authMethod.getType()) {
						if (ignoreTypes == null)
							ignoreTypes = new HashSet<Type>();

						ignoreTypes.add(authMethod.getType());

						authMethod = HttpAuthMethod.Type.NONE.method(null);
						authAttempts = 1;

						continue;
					}

					throw e;
				}

