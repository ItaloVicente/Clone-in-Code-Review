		if (db.getConfig().get(ServiceConfig::new).enabled) {
			UploadPack up = new UploadPack(db);
			if (header != null) {
				up.setExtraParameters(Arrays.asList(params));
			}
			return up;
		} else {
