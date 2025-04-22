			} catch (IOException | InterruptedException e) {
				throw new IOException(new FilterFailedException(e,
						checkoutMetadata.smudgeFilterCommand,
						entry.getPathString()));

			} finally {
				channel.close();
			}
			if (rc != 0) {
				throw new IOException(new FilterFailedException(rc,
						checkoutMetadata.smudgeFilterCommand,
						entry.getPathString(),
						result.getStdout().toByteArray(MAX_EXCEPTION_TEXT_SIZE),
						RawParseUtils.decode(result.getStderr()
								.toByteArray(MAX_EXCEPTION_TEXT_SIZE))));
			}
		} else {
			try {
