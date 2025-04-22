					@Override
					public boolean get(URIish uri, CredentialItem... items)
							throws UnsupportedCredentialItem {
						return false;
					}
				};
				if (signer instanceof GpgObjectSigner) {
					return ((GpgObjectSigner) signer).canLocateSigningKey(
							config.getSigningKey(), personIdent, credentials,
							config);
				}
				return signer.canLocateSigningKey(config.getSigningKey(),
						personIdent, credentials);
