			remaining -= n;
		}

		if (md != null) {
			byte[] buf = new byte[20];
			byte[] actHash = md.digest();

			pin(pack
			if (window.copy(position
				pack.setInvalid();
				throw new IOException(JGitText.get().packfileIsTruncated);
			}
			if (!Arrays.equals(actHash
				pack.setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileCorruptionDetected
								.getPackFile().getPath()));
			}
