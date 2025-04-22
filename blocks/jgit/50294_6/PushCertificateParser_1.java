	PushCertificateParser(Repository into
		if (cfg != null) {
			nonceSlopLimit = cfg.getCertNonceSlopLimit();
			nonceGenerator = cfg.getCertNonceSeed() != null
					? new HMACSHA1NonceGenerator(cfg.certNonceSeed)
					: null;
		} else {
			nonceSlopLimit = 0;
			nonceGenerator = null;
		}
