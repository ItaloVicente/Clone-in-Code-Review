				try (ByteArrayInputStream in = new ByteArrayInputStream(
						hostKey)) {
					return Collections.singletonList(
							SecurityUtils.loadKeyPairIdentity("", in, null));
				} catch (IOException | GeneralSecurityException e) {
					return null;
				}
