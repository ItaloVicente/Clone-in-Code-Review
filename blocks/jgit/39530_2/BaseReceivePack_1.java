		certNonceSeed = cfg.certNonceSeed;
		if (!certNonceSeed.isEmpty())
			pushCertNonce = preparePushCertNonce(
					into.getDirectory().getPath()
					new Date(0).getTime() / milliSecondsPerSecond);
		certNonceSlopLimit = cfg.certNonceSlopLimit;
