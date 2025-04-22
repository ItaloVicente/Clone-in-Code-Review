				if (signer instanceof GpgObjectSigner) {
					((GpgObjectSigner) signer).signObject(builder,
							config.getSigningKey(), committer,
							CredentialsProvider.getDefault(), config);
				} else {
					signer.sign(builder, config.getSigningKey(), committer,
							CredentialsProvider.getDefault());
				}
