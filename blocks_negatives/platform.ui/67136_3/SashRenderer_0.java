			element.setContainerData(Integer.toString(10000));
			info = element.getContainerData();
		}

		try {
			int value = Integer.parseInt(info);
			return value;
		} catch (NumberFormatException e) {
			return UNDEFINED_WEIGHT;
