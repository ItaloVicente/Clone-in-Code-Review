		try {
			super.handleDispose();
		} finally {
			for (Color color : customColors.values()) {
				if (color != null) {
					color.dispose();
				}
