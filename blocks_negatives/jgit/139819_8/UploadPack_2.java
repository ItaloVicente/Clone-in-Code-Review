		} catch (ServiceMayNotContinueException err) {
			if (!err.isOutput() && err.getMessage() != null) {
				try {
				} catch (IOException e) {
					err.addSuppressed(e);
					throw err;
				}
				err.setOutput();
			}
			throw err;
		} catch (IOException | RuntimeException | Error err) {
			String msg = err instanceof PackProtocolException ? err.getMessage()
					: JGitText.get().internalServerError;
			try {
			} catch (IOException e) {
				err.addSuppressed(e);
				throw err;
			}
			throw new UploadPackInternalServerErrorException(err);
