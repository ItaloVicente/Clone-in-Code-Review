					if (index > 0)
						text = text.substring(0, index);
					URIish u = new URIish(text);
					if (Transport.canHandleProtocol(u, FS.DETECTED)) {
						String s = u.getScheme();
						if (s.equals(DEFAULT_SCHEMES[S_GIT])
								|| s.equals(DEFAULT_SCHEMES[S_SSH])
								|| text.endsWith(Constants.DOT_GIT))
