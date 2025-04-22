		if (parent != null) {
			return super.asPath();
		}
		if (bean == null) {
			return "null()";
		}
		if (bean instanceof Number) {
			String string = bean.toString();
			if (string.endsWith(".0")) {
				string = string.substring(0, string.length() - 2);
			}
			return string;
		}
		if (bean instanceof Boolean) {
			return ((Boolean) bean).booleanValue() ? "true()" : "false()";
		}
		if (bean instanceof String) {
			return "'" + bean + "'";
		}
		return "/";
	}
