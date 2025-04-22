			try {
				sendPack(pm, packOut, req, accumulator, allTags,
						unshallowCommits, deepenNots);
				pckOut.end();
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
			} catch (IOException | RuntimeException | Error err) {
				try {
					reportInternalServerErrorOverSideband(
							JGitText.get().internalServerError);
				} catch (IOException e) {
					err.addSuppressed(e);
					throw err;
				}
				throw new UploadPackInternalServerErrorException(err);
			}
