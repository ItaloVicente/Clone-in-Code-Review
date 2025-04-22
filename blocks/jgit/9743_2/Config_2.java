		if (all[0] instanceof ConfigEnum) {
			for (T t : all) {
				if (((ConfigEnum) t).matchConfigValue(value))
					return t;
			}
		}

