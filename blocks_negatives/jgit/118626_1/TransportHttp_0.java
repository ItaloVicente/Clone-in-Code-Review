			try {
				final BufferedReader br = openReader(INFO_PACKS);
				try {
					for (;;) {
						final String s = br.readLine();
						if (s == null || s.length() == 0)
							break;
							throw invalidAdvertisement(s);
						packs.add(s.substring(2));
					}
					return packs;
				} finally {
					br.close();
