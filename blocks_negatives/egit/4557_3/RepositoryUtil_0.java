			String dirString;
			try {
				dirString = repositoryDir.getCanonicalPath();
			} catch (IOException e) {
				dirString = repositoryDir.getAbsolutePath();
			}
