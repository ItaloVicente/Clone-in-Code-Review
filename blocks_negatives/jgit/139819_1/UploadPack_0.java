		} catch (ServiceMayNotContinueException err) {
			if (!err.isOutput() && err.getMessage() != null) {
				err.setOutput();
			}
			throw err;
		} catch (IOException | RuntimeException | Error err) {
			String msg = err instanceof PackProtocolException ? err.getMessage()
					: JGitText.get().internalServerError;
			throw new UploadPackInternalServerErrorException(err);
