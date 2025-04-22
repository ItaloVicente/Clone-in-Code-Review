		if (hostname1.length() == 0)
			hostname1 = resolvedHostName;

		if (hostname2.length() == 0)
			hostname2 = resolvedHostName;

		if (hostname1.equals(hostname2))
			return true;

		if ((hostname1.equals(localHost) && hostname2.equals(resolvedHostName))
				|| (hostname1.equals(resolvedHostName) && hostname2.equals(localHost)))
			return true;

		return false;
