
		ExecutorService executor = Executors.newSingleThreadExecutor();

		long selectObjectMaxTimeMSec = packer.getFindBestPackRepresentation()
				? Long.MAX_VALUE
				: config.getLong(
					ConfigConstants.CONFIG_PACK_SECTION
					ConfigConstants.CONFIG_KEY_SELECT_OBJECT_MAX_TIME_MSEC

		try {
			executor
				.submit(new SelectRepresentationTask(packer
				.get(selectObjectMaxTimeMSec
		} catch (TimeoutException toe) {
			LOG.warn("Giving up object selection. It is taking more than " + selectObjectMaxTimeMSec + "ms"
		} catch (InterruptedException | ExecutionException e) {
			LOG.error("Cannot select objects representation for " + otp.getName()
		}
