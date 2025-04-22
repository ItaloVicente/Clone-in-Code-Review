			try {
				sendPack(true, req, accumulator, allTags, unshallowCommits,
						deepenNots, pckOut);
			} catch (ServiceMayNotContinueException err) {
				String message = err.getMessage();
				if (message == null) {
					message = JGitText.get().internalServerError;
				}
				try {
					errOut.writeError(message);
				} catch (IOException e) {
					err.addSuppressed(e);
					throw err;
				}
				throw new UploadPackInternalServerErrorException(err);
			} catch (IOException | RuntimeException | Error err) {
				try {
					errOut.writeError(JGitText.get().internalServerError);
				} catch (IOException e) {
					err.addSuppressed(e);
					throw err;
				}
				throw new UploadPackInternalServerErrorException(err);
