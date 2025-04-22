			Location instanceLoc = Platform.getInstanceLocation();
			if (instanceLoc != null)
				instanceLoc.release();
		}
	}

	protected Display createDisplay() {
		return PlatformUI.createDisplay();
	}

	@Override
