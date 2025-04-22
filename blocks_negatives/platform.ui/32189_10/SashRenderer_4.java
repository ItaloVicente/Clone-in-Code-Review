	private static int getWeight(MUIElement element) {
		String info = element.getContainerData();
		if (info == null || info.length() == 0) {
			element.setContainerData(Integer.toString(10000));
			info = element.getContainerData();
		}

		try {
			int value = Integer.parseInt(info);
			return value;
		} catch (NumberFormatException e) {
			return UNDEFINED_WEIGHT;
		}
	}
