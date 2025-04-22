		if (defaultValue instanceof ConfigEnum) {
			for (T t : all) {
				if (((ConfigEnum) t).matchConfigValue(value))
					return t;
			}
		}

