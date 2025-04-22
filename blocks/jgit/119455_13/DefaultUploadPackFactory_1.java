		if (db.getConfig().get(ServiceConfig::new).enabled) {
			UploadPack up = new UploadPack(db);
			String header = req.getHeader("Git-Protocol");
			if (header != null) {
				up.setExtraParameters(Arrays.asList(header.split(":")));
			}
			return up;
		} else
