		boolean checkoutOk = false;
		try {
			checkoutOk = checkoutCommit(upstreamCommit);
		} finally {
			if (!checkoutOk)
				FileUtils.delete(rebaseDir
		}
