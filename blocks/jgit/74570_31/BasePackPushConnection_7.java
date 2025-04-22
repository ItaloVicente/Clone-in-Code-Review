
			OutputStream packOut = out;
			if (capableSideBand) {
				packOut = new CheckingSideBandOutputStream(in
			}
			writer.writePack(monitor
