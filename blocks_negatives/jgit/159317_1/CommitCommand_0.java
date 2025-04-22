		if (gpgSigner == null) {
			if (gpgConfig.getKeyFormat() != GpgFormat.OPENPGP) {
				throw new UnsupportedSigningFormatException(
						JGitText.get().onlyOpenPgpSupportedForSigning);
			}
			gpgSigner = GpgSigner.getDefault();
