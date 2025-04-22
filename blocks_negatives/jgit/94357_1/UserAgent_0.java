		Package pkg = UserAgent.class.getPackage();
		if (pkg != null) {
			String ver = pkg.getImplementationVersion();
			if (!StringUtils.isEmptyOrNull(ver)) {
				return ver;
			}
		}
