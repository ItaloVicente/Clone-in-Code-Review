		return true;
	}

	@Override
	public void dispose() {
		window = null;
		selectBuildWorkingSetAction = null;
		actionBars = null;
		super.dispose();
	}
