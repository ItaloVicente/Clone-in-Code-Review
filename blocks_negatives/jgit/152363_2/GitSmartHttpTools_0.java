
		boolean sideband;
		UploadPack up = (UploadPack) req.getAttribute(ATTRIBUTE_HANDLER);
		if (up != null) {
			try {
				sideband = up.isSideBand();
			} catch (RequestNotYetReadException e) {
				sideband = isUploadPackSideBand(req);
			}
		} else
			sideband = isUploadPackSideBand(req);

		if (sideband)
			writeSideBand(buf, textForGit);
		else
			writePacket(pckOut, textForGit);
