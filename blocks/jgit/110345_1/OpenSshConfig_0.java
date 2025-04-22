	private static File toFile(String path
			return new File(home
		}
		File ret = new File(path);
		if (ret.isAbsolute()) {
			return ret;
		}
		return new File(home
	}

