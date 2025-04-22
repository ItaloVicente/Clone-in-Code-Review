
			if (enable && !(db.getRefDatabase() instanceof RefTreeDatabase)) {
				StoredConfig cfg = db.getConfig();
				cfg.setInt("core"
				cfg.setString("extensions"
				cfg.save();
				errw.flush();
			}
