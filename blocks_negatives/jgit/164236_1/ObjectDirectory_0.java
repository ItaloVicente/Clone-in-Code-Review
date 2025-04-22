			FileUtils.mkdir(dst.getParentFile(), true);
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			FileUtils.delete(tmp, FileUtils.RETRY);
			return InsertLooseObjectResult.FAILURE;
		}

		try {
			return tryMove(tmp, dst, id);
