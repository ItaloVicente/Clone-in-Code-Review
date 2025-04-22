	private void sendPack(V0V2PackWriter packWriter
			@Nullable Collection<Ref> allTags
			List<ObjectId> deepenNots) throws IOException {
		if (wantAll.isEmpty()) {
			preUploadHook.onSendPack(this
		} else {
			preUploadHook.onSendPack(this
		}
		if (packConfig != null) {
			packWriter.setPackConfig(packConfig);
		}
		try {
			packWriter.sendPack(allTags
					wantAll
		} finally {
			statistics = packWriter.getStatistics();
			if (statistics != null) {
				postUploadHook.onPostUpload(statistics);
			}
		}
	}

