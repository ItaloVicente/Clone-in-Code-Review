										JGitText.get().authenticationNotSupported,
										conn.getURL()));
					case NEGOTIATE:
						ignoreTypes.add(HttpAuthMethod.Type.NEGOTIATE);
						if (authenticator != null) {
							ignoreTypes.add(authenticator.getType());
