		if (db.getConfig().get(ServiceConfig::new).enabled) {
			UploadPack up = new UploadPack(db);
			String header = req.getHeader("Git-Protocol");
			if (db.getConfig().getInt("protocol"
					(":" + header + ":").contains(":version=2:")) {
				up.setUseProtocolV2(true);
			}
			return up;
		} else
