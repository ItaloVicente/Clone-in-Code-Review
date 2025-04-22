				case HttpConnection.HTTP_MOVED_PERM:
				case HttpConnection.HTTP_MOVED_TEMP:
				case HttpConnection.HTTP_SEE_OTHER:
				case HttpConnection.HTTP_11_MOVED_TEMP:
					if (http.followRedirects == HttpRedirectMode.FALSE) {
						throw new TransportException(MessageFormat.format(
								JGitText.get().redirectsOff
								Integer.valueOf(status)));
					}
					URIish newUri = redirect(conn.getHeaderField(HDR_LOCATION)
							Constants.INFO_REFS
					setURI(newUri);
					u = getServiceURL(service);
					authAttempts = 1;
					break;
