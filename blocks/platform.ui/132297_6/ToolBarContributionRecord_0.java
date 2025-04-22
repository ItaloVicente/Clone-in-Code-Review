	ExpressionInfo getExpressionInfo() {
		if (info == null) {
			collectInfo();
		}
		return info;
	}

