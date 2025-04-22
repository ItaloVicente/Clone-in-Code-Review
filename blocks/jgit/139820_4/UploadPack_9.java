			} catch (ServiceMayNotContinueException err) {
				String message = err.getMessage();
				if (message == null) {
					message = JGitText.get().internalServerError;
				}
				try {
					reportInternalServerErrorOverSideband(message);
				} catch (IOException e) {
					err.addSuppressed(e);
					throw err;
				}
				throw new UploadPackInternalServerErrorException(err);
