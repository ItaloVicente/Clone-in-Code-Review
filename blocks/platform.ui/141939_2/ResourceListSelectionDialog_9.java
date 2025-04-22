		boolean result = super.close();
		labelProvider.dispose();
		return result;
	}

	@Override
