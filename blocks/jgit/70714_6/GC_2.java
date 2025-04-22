
	public void setAuto(boolean auto) {
		this.automatic = auto;
	}

	private boolean needGc() {
		if (tooManyPacks()) {
			addRepackAllOption();
		} else if (!tooManyLooseObjects()) {
			return false;
		}
		return true;
	}

	private void addRepackAllOption() {
	}

	private boolean tooManyPacks() {
		int autopacklimit = repo.getConfig().getInt(
				ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTOPACKLIMIT
				DEFAULT_AUTOPACKLIMIT);
		if (autopacklimit <= 0) {
			return false;
		}
		return repo.getObjectDatabase().getPacks().size() > (autopacklimit + 1);
	}

	private boolean tooManyLooseObjects() {
		int auto = repo.getConfig().getInt(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_AUTO
		if (auto <= 0) {
			return false;
		}
		int n = 0;
		int threshold = (auto + 255) / 256;
		if (!Files.exists(dir)) {
			return false;
		}
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir
				new DirectoryStream.Filter<Path>() {

					public boolean accept(Path file) throws IOException {
						return Files.isRegularFile(file) && PATTERN_LOOSE_OBJECT
								.matcher(file.getFileName().toString())
								.matches();
					}
				})) {
			Iterator<Path> iter = stream.iterator();
			while (iter.hasNext()) {
				if (n++ > threshold) {
					return true;
				}
			}
		} catch (IOException e) {
			LOG.error(e.getMessage()
		}
		return false;
	}
