		alreadyClosed = true;// As this sometimes delayed cache if it was already closed
		boolean result = super.close();
		if (!result) {// If it fails reset the flag
			alreadyClosed = false;
		}
		return result;
	}

	@Override
