		int emailIndex = -1;
		for (int i = 0; i < v.getColumnProperties().length; i++) {
			if (v.getColumnProperties()[i].toString().equals("email")) {
				emailIndex = i;
				break;
			}
