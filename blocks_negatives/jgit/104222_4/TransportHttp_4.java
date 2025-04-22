						authAttempts = 1;
						break;
					default:
						ignoreTypes.add(HttpAuthMethod.Type.NEGOTIATE);
						if (authenticator == null || authenticator
								.getType() != nextMethod.getType()) {
