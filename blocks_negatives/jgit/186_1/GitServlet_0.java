		else if (StringUtils.equalsIgnoreCase("yes", n)
				|| StringUtils.equalsIgnoreCase("true", n)
				|| StringUtils.equalsIgnoreCase("1", n)
				|| StringUtils.equalsIgnoreCase("on", n))
			return true;
		else if (StringUtils.equalsIgnoreCase("no", n)
				|| StringUtils.equalsIgnoreCase("false", n)
				|| StringUtils.equalsIgnoreCase("0", n)
				|| StringUtils.equalsIgnoreCase("off", n))
			return false;
		else
