	private boolean checkMacBug517231() {
		if (Platform.OS_MACOSX.equals(Platform.getOS())) {
			System.out.println("skipping " + PartRenderingEngineTests.class.getName() + "#"
					+ this.getClass().getSimpleName() + " on Mac for now, see bug 517231");
			return true;
		}
		return false;
	}

