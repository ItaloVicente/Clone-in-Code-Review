		long lastMtime = configFile.lastModified();
		do {
			try (final OutputStreamWriter fw = new OutputStreamWriter(
					new FileOutputStream(configFile)
				fw.write(data);
			}
		} while (lastMtime == configFile.lastModified());
