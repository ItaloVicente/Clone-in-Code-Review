		StatefulProxyConnector proxy = proxyHandler;
		if (proxy != null) {
			try {
				proxy.runWhenDone(() -> {
					JGitClientSession.super.sendIdentification(ident);
					return null;
				});
				return null;
			} catch (IOException e) {
				throw e;
			} catch (Exception other) {
				throw new IOException(other.getLocalizedMessage()
			}
		} else {
			return super.sendIdentification(ident);
		}
