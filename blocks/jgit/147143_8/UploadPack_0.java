	public void upload(InputStream input
			@Nullable OutputStream messages) throws IOException {
		try {
			uploadWithExceptionPropagation(input
		} catch (ServiceMayNotContinueException err) {
			if (!err.isOutput() && err.getMessage() != null) {
				try {
					errOut.writeError(err.getMessage());
				} catch (IOException e) {
					err.addSuppressed(e);
					throw err;
				}
				err.setOutput();
			}
			throw err;
		} catch (IOException | RuntimeException | Error err) {
			if (rawOut != null) {
				String msg = err instanceof PackProtocolException
						? err.getMessage()
						: JGitText.get().internalServerError;
				try {
					errOut.writeError(msg);
				} catch (IOException e) {
					err.addSuppressed(e);
					throw err;
				}
				throw new UploadPackInternalServerErrorException(err);
			}
			throw err;
		}
	}

