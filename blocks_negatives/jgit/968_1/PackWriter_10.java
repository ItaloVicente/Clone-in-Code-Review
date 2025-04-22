	private PackedObjectLoader open(final LocalObjectToPack otp) throws IOException {
		while (otp.isReuseAsIs()) {
			try {
				PackedObjectLoader reuse = otp.getCopyLoader(windowCursor);
				reuse.beginCopyRawData();
				return reuse;
			} catch (IOException err) {
				otp.clearDeltaBase();
				otp.clearReuseAsIs();
				windowCursor.selectObjectRepresentation(this, otp);
				continue;
