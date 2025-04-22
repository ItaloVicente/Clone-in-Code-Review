		if ("file".equals(uri.getScheme()) || uri.getScheme() == null) {
			if (fs.resolve(new File(PWD)
				return true;
			} else {
				throw new NotSupportedException(uri.getPath() + " does not exist");
			}
		}
