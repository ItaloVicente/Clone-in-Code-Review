		String info = element.getContainerData();
		if (info != null && info.length() > 0) {
			try {
				int value = Integer.parseInt(info);
				weight = value;
			} catch (NumberFormatException e) {
			}
