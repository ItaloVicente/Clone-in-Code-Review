			final InetAddress peer = req.getRemoteAddress();
			String host = peer.getCanonicalHostName();
			if (host == null) {
				host = peer.getHostAddress();
			}
			final String name = "anonymous";
			final String email = name + "@" + host;
			rp.setRefLogIdent(new PersonIdent("system"
			rp.setTimeout(getTimeout());
