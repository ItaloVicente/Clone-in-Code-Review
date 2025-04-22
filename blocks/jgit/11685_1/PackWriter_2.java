	private void reselectNonDelta(ObjectToPack otp) throws IOException {
		otp.clearDeltaBase();
		otp.clearReuseAsIs();
		boolean old = reuseDeltas;
		reuseDeltas = false;
		reuseSupport.selectObjectRepresentation(this
				NullProgressMonitor.INSTANCE
				Collections.singleton(otp));
		reuseDeltas = old;
	}

