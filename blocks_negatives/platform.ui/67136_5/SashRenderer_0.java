	private static int getLayoutWeight(MUIElement element) {
		String info = element.getContainerData();
		if (info == null || info.length() == 0) {
			element.setContainerData(Integer.toString(10000));
			info = element.getContainerData();
		}
