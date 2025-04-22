			if (portForward != null) {
				IoUtils.closeQuietly(portForward);
			}
			if (proxySession != null) {
				proxySession.close();
			}
			if (resultSession != null) {
				resultSession.close();
			}
