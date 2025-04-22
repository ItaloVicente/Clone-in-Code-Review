			} catch (ServiceMayNotContinueException err) {
				String message = err.getMessage();
				if (message == null) {
					message = JGitText.get().internalServerError;
				}
				if (reportInternalServerErrorOverSideband(message)) {
