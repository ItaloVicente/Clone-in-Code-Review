		} catch (IOException | RuntimeException | Error err) {
			boolean output = false;
			try {
				String msg = err instanceof PackProtocolException
						? err.getMessage()
						: JGitText.get().internalServerError;
				output = true;
			} catch (Throwable err2) {
			}
			if (output) {
				throw new UploadPackInternalServerErrorException(err);
			}
