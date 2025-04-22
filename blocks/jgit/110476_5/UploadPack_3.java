				String arg = line.substring(OPTION_BLOB_MAX_BYTES.length() + 1);
				try {
					blobMaxBytes = Long.parseLong(arg);
				} catch (NumberFormatException e) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidBlobMaxBytes
									arg));
				}
				if (blobMaxBytes < 0) {
					throw new PackProtocolException(
							MessageFormat.format(JGitText.get().invalidBlobMaxBytes
									arg));
				}
				continue;
			}

