		SideBandInputStream sidebandIn = null;
		if (sideband) {
			sidebandIn = new SideBandInputStream(input
					getMessageWriter()
			input = sidebandIn;
		}
