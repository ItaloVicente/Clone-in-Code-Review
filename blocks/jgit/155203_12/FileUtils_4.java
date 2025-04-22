
	public static File resolveFile(FS fs
		File file;
			file = fs.resolve(fs.userHome()
		} else {
			file = fs.resolve(null
		}
		return file;
	}
