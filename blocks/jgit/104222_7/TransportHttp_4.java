										JGitText.get().serviceNotPermitted
										baseUrl

					case HttpConnection.HTTP_MOVED_PERM:
					case HttpConnection.HTTP_MOVED_TEMP:
					case HttpConnection.HTTP_11_MOVED_TEMP:
						if (http.getFollowRedirects() != HttpRedirectMode.TRUE) {
							return;
