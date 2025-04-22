		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(in, UTF_8))) {
			for (String s = br.readLine(); s != null; s = br.readLine()) {
					continue;
					if (versionLine || s.length() < 8 || s.charAt(7) != ' ') {
					}
					String rest = s.substring(8).trim();
					versionLine = VERSION.equals(rest)
							|| VERSION_LEGACY.equals(rest);
					if (!versionLine) {
					}
				} else {
					try {
							if (id != null) {
							}
							id = LongObjectId
									.fromString(s.substring(11).trim());
							if (sz > 0 || s.length() < 5
									|| s.charAt(4) != ' ') {
							}
							sz = Long.parseLong(s.substring(5).trim());
