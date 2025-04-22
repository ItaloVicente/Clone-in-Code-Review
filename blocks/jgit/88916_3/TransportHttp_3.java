				String locationHeader = HttpSupport.responseHeader(conn
						HDR_LOCATION);
				switch (http.getRedirect()) {
				case TRUE:
					sendRequest(locationHeader);
					break;
				case FALSE:
					throw new RedirectForbiddenException(MessageFormat.format(
							JGitText.get().redirectForbidden
							http.getRedirect().name()));
				}
