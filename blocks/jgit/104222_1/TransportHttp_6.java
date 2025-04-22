							break;
						default:
							ignoreTypes.add(HttpAuthMethod.Type.NEGOTIATE);
							if (authenticator == null || authenticator
									.getType() != nextMethod.getType()) {
								if (authenticator != null) {
									ignoreTypes.add(authenticator.getType());
								}
								authAttempts = 1;
							}
							break;
