		private static String uri(URL u) {
			StringBuilder r = new StringBuilder();
			r.append(u.getProtocol());
			r.append(u.getHost());
			if (0 < u.getPort()) {
				if (u.getPort() == 80 && "http".equals(u.getProtocol()))
				else if (u.getPort() == 443 && "https".equals(u.getProtocol()))
				else
					r.append(':').append(u.getPort());
			}
			r.append(u.getPath());
			if (u.getQuery() != null)
				r.append('?').append(u.getQuery());
			return r.toString();
		}

