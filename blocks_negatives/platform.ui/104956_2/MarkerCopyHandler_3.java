		Object[] data;
		Transfer[] transferTypes;
		if (markerReport == null) {
			data = new Object[] { markers };
			transferTypes = new Transfer[] { MarkerTransfer.getInstance() };
		} else {
			data = new Object[] { markers, markerReport };
			transferTypes = new Transfer[] { MarkerTransfer.getInstance(),
					TextTransfer.getInstance() };
		}
