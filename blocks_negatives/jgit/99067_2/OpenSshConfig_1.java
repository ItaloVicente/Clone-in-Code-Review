			try {
				final FileInputStream in = new FileInputStream(configFile);
				try {
					hosts = parse(in);
				} finally {
					in.close();
				}
			} catch (FileNotFoundException none) {
				hosts = Collections.emptyMap();
			} catch (IOException err) {
				hosts = Collections.emptyMap();
