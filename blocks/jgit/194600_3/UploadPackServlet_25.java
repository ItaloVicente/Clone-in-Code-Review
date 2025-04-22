				String msg = null;
				if (e instanceof PackProtocolException
						|| e instanceof ServiceNotEnabledException) {
					msg = e.getMessage();
				}
				sendError(req
