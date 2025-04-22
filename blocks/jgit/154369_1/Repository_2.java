	@NonNull
	public Mailmap getMailmap() {
		if (mailmap == null) {
			mailmap = new Mailmap();
			if (!isBare() && getDirectory() != null) {
				File workTree = getWorkTree();
				File configuredMailmapFile = Optional
						.ofNullable(getConfig().getString("mailmap"
						.map(filePath -> {
							File f = new File(filePath);
							if (!f.isAbsolute()) {
								f = new File(workTree
							}

							return f;
						})
						.orElse(null);

				if (configuredMailmapFile != null && configuredMailmapFile.exists()) {
					try {
						Mailmap configuredMailmap = MailmapParser.parse(configuredMailmapFile);
						mailmap.append(configuredMailmap);
					} catch (IOException ignored) {
					}
				}

				File defaultMailmapFile = new File(workTree
				if (!defaultMailmapFile.equals(configuredMailmapFile) && defaultMailmapFile
						.exists()) {
					try {
						Mailmap defaultMailmap = MailmapParser.parse(defaultMailmapFile);
						mailmap.append(defaultMailmap);
					} catch (IOException ignored) {
					}
				}
			}
		}

		return mailmap;
	}

