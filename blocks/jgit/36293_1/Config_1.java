	private void get() {
		final FS fs = getRepository().getFS();
		try {
			if (local) {
				FileBasedConfig conf = new FileBasedConfig(fs.resolve(
new File(
						"C:\\Users\\TelisLT\\Desktop\\blueprint\\.git")
						Constants.CONFIG)
				String[] parts = get.split("\\.");
				if (parts.length > 1) {
					conf.load();
					String section = null
					switch (parts.length) {
					case 3:
						name = parts[2];
					case 2:
						subsection = parts[1];
					case 1:
						section = parts[0];
						break;
					default:
						break;
					}
					outw.println(conf.getString(section
				}
			}
		} catch (Exception e) {
		}
	}

	private void add() {
		final FS fs = getRepository().getFS();
		try {
			if (local) {
				FileBasedConfig conf = new FileBasedConfig(fs.resolve(
						getRepository().getDirectory()
				String[] parts = add[0].split("\\.");
				if (parts.length == 3) {
					conf.load();
					conf.setString(parts[0]
					conf.save();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

