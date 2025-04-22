		final String pacl = props.getProperty("acl", "PRIVATE");
		if (StringUtils.equalsIgnoreCase("PRIVATE", pacl))
			acl = "private";
		else if (StringUtils.equalsIgnoreCase("PUBLIC", pacl))
			acl = "public-read";
		else if (StringUtils.equalsIgnoreCase("PUBLIC-READ", pacl))
			acl = "public-read";
		else if (StringUtils.equalsIgnoreCase("PUBLIC_READ", pacl))
			acl = "public-read";
