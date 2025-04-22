			try {
				if (text != null) {
					text = stripGitCloneCommand(text);
					text = text.split(
							"[ \\f\\n\\r\\x0B\\t\\xA0\\u1680\\u180e\\u2000-\\u200a\\u202f\\u205f\\u3000]", //$NON-NLS-1$
							2)[0];
					URIish u = new URIish(text);
					if (canHandleProtocol(u)) {
						if (Protocol.GIT.handles(u) || Protocol.SSH.handles(u)
								|| (Protocol.HTTP.handles(u)
										|| Protocol.HTTPS.handles(u))
										&& KnownHosts.isKnownHost(u.getHost())
								|| text.endsWith(Constants.DOT_GIT_EXT)) {
							preset = text;
						}
					}
