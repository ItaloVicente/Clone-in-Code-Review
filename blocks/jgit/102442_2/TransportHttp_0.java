				case HttpConnection.HTTP_MOVED_PERM:
					String locationHeader = HttpSupport.responseHeader(conn
					if (locationHeader == null) {
						throw new TransportException(uri
					}
					u = new URL(locationHeader);
					continue;

