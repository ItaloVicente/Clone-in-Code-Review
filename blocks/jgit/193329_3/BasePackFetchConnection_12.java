
		if (GitProtocolConstants.SECTION_SHALLOW_INFO.equals(line)) {
			handleShallowUnshallow(pckIn);
			line = pckIn.readString();
		}

