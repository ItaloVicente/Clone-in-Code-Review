		private File toFile(String path, File home) {
				return new File(home, path.substring(2));
			}
			File ret = new File(path);
			if (ret.isAbsolute()) {
				return ret;
			}
			return new File(home, path);
		}

