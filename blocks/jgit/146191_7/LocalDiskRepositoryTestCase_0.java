		try {
			FileUtils.delete(dir
		} catch (IOException e) {
			reportDeleteFailure(failOnError
			return !failOnError;
		}
		return true;
