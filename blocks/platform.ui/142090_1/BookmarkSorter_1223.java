		try {
			for (int i = 0; i < priorities.length; i++) {
				priorities[i] = settings.getInt("priority" + i);//$NON-NLS-1$
				directions[i] = settings.getInt("direction" + i);//$NON-NLS-1$
			}
		} catch (NumberFormatException e) {
			resetState();
		}
	}
