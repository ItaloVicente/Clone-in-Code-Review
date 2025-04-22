
		ExecutorService executor = Executors.newSingleThreadExecutor();

		int selectObjectMaxTimeMsec = config.getInt(
				ConfigConstants.CONFIG_PACK_SECTION
				ConfigConstants.CONFIG_KEY_SELECT_OBJECT_MAX_TIME_MSEC

		if (selectObjectMaxTimeMsec > 0) {
			try {
				executor.submit(new SelectRepresentationTask(packer
			} catch (TimeoutException toe) {
				toe.printStackTrace();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		} else {
			packed.selectRepresentation(packer
		}
