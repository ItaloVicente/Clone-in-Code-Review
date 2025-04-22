
		if (gitExe == null) {
			if (SystemReader.getInstance().isMacOS()) {
				if (searchPath(path
					String w = readPipe(userHome()
							new String[]{"bash"
							Charset.defaultCharset().name());
					if (!StringUtils.isEmptyOrNull(w))
						gitExe = new File(w);
				}
