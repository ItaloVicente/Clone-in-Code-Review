	@NonNull
	public Mailmap getMailmap() {
		if (mailmap == null) {
			mailmap = new Mailmap();
			if (!isBare() && getDirectory() != null) {
				File configuredMailmapFile = Optional
						.ofNullable(getConfig().getString(
								ConfigConstants.CONFIG_MAILMAP_SECTION
								ConfigConstants.CONFIG_KEY_FILE))
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
					} catch (IOException e) {
						LOG.error(e.getMessage()
					}
				}

				File defaultMailmapFile = new File(workTree
						Constants.MAILMAP_FILENAME);
				if (!defaultMailmapFile.equals(configuredMailmapFile) && defaultMailmapFile
						.exists()) {
					try {
						Mailmap defaultMailmap = MailmapParser.parse(defaultMailmapFile);
						mailmap.append(defaultMailmap);
					} catch (IOException e) {
						LOG.error(e.getMessage()
					}
				}
			}
		}

		return mailmap;
	}

