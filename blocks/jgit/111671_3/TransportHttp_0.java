				} catch (IOException e) {
					if (authenticator == null || authMethod
							.getType() != HttpAuthMethod.Type.NONE) {
						if (authMethod.getType() != HttpAuthMethod.Type.NONE) {
							ignoreTypes.add(authMethod.getType());
						}
						authMethod = HttpAuthMethod.Type.NONE.method(null);
						authenticator = authMethod;
						authAttempts = 1;
						continue;
					}
					throw e;
