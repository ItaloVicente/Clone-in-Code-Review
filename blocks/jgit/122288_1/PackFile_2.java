	public void delete() {
		try {
			int deleteOptions = FileUtils.RETRY | FileUtils.SKIP_MISSING;
			FileUtils.delete(names.get(PACK)

			deleteOptions |= FileUtils.IGNORE_ERRORS;
			for (PackFileName name : names.values()) {
				if (!PACK.equals(name.getPackExt())) {
					FileUtils.delete(name
				}
			}
		} catch (IOException e) {
		}
	}

