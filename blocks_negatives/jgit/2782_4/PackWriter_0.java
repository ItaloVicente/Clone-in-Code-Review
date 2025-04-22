		ObjectToPack baseInPack = otp.getDeltaBase();
		if (baseInPack != null) {
			if (!baseInPack.isWritten()) {
				if (baseInPack.wantWrite()) {
					reuseDeltas = false;
					redoSearchForReuse(otp);
					reuseDeltas = true;
				} else {
					writeObject(out, baseInPack);
				}
			}
		} else if (!thin) {
			otp.clearDeltaBase();
			otp.clearReuseAsIs();
		}
	}

	private void redoSearchForReuse(final ObjectToPack otp) throws IOException,
			MissingObjectException {
		otp.clearDeltaBase();
		otp.clearReuseAsIs();
		reuseSupport.selectObjectRepresentation(this,
				NullProgressMonitor.INSTANCE, Collections.singleton(otp));
