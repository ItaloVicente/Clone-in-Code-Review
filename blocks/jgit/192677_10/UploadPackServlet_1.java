				String msg = null;
				if (e instanceof PackProtocolException) {
					msg = e.getMessage();
				}
				if (e instanceof ServiceNotEnabledException) {
					msg = e.getMessage();
				}
				sendError(req
