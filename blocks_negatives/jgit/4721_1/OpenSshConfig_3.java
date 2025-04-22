	private synchronized Map<String, Host> refresh() {
		final long mtime = configFile.lastModified();
		if (mtime != lastModified) {
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
			}
			lastModified = mtime;
		}
		return hosts;
	}

	private Map<String, Host> parse(final InputStream in) throws IOException {
		final Map<String, Host> m = new LinkedHashMap<String, Host>();
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		final List<Host> current = new ArrayList<Host>(4);
		String line;

		while ((line = br.readLine()) != null) {
			line = line.trim();
			if (line.length() == 0 || line.startsWith("#"))
				continue;

			final String[] parts = line.split("[ \t]*[= \t]", 2);
			final String keyword = parts[0].trim();
			final String argValue = parts[1].trim();

			if (StringUtils.equalsIgnoreCase("Host", keyword)) {
				current.clear();
				for (final String pattern : argValue.split("[ \t]")) {
					final String name = dequote(pattern);
					Host c = m.get(name);
					if (c == null) {
						c = new Host();
						m.put(name, c);
					}
					current.add(c);
				}
				continue;
			}

			if (current.isEmpty()) {
				continue;
			}

			if (StringUtils.equalsIgnoreCase("HostName", keyword)) {
				for (final Host c : current)
					if (c.hostName == null)
						c.hostName = dequote(argValue);
			} else if (StringUtils.equalsIgnoreCase("User", keyword)) {
				for (final Host c : current)
					if (c.user == null)
						c.user = dequote(argValue);
			} else if (StringUtils.equalsIgnoreCase("Port", keyword)) {
				try {
					final int port = Integer.parseInt(dequote(argValue));
					for (final Host c : current)
						if (c.port == 0)
							c.port = port;
				} catch (NumberFormatException nfe) {
				}
			} else if (StringUtils.equalsIgnoreCase("IdentityFile", keyword)) {
				for (final Host c : current)
					if (c.identityFile == null)
						c.identityFile = toFile(dequote(argValue));
			} else if (StringUtils.equalsIgnoreCase("PreferredAuthentications", keyword)) {
				for (final Host c : current)
					if (c.preferredAuthentications == null)
						c.preferredAuthentications = nows(dequote(argValue));
			} else if (StringUtils.equalsIgnoreCase("BatchMode", keyword)) {
				for (final Host c : current)
					if (c.batchMode == null)
						c.batchMode = yesno(dequote(argValue));
			} else if (StringUtils.equalsIgnoreCase("StrictHostKeyChecking", keyword)) {
				String value = dequote(argValue);
				for (final Host c : current)
					if (c.strictHostKeyChecking == null)
						c.strictHostKeyChecking = value;
			}
		}

		return m;
	}

