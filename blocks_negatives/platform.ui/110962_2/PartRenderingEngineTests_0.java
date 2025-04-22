	private boolean checkMacBug466636() {
		if (Platform.OS_MACOSX.equals(Platform.getOS())) {
			System.out.println("skipping " + PartRenderingEngineTests.class.getName() + "#"
					+ this.getClass().getSimpleName()
					+ " on Mac for now, see bug 466636");
			return true;
		}
		return false;
	}
