		} catch (UploadPackInternalServerErrorException err) {
			throw err;
		} catch (ServiceMayNotContinueException err) {
			if (!err.isOutput() && err.getMessage() != null && pckOut != null) {
				err.setOutput();
			}
			throw err;
		} catch (IOException | RuntimeException | Error err) {
			if (pckOut != null) {
				String msg = err instanceof PackProtocolException
						? err.getMessage()
						: JGitText.get().internalServerError;
				throw new UploadPackInternalServerErrorException(err);
			}
			throw err;
