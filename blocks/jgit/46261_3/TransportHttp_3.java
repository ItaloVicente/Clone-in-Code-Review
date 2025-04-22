				case HttpConnection.HTTP_MOVED_PERM:
				case HttpConnection.HTTP_MOVED_TEMP:
					String location = conn.getHeaderField(HDR_LOCATION);
					try {
						LOG.info(JGitText.get().redirectHttp
						int infoRefs = location.indexOf(Constants.INFO_REFS);
						if (infoRefs != -1) {
							location = location.substring(0
						}
						setURI(new URIish(location));
						u = getServiceURL(service);
						authAttempts = 1;
					} catch (URISyntaxException e) {
						throw new TransportException(MessageFormat.format(
								JGitText.get().invalidRedirectLocation
								location)
					}
					break;
