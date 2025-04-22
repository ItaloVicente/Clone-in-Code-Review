	protected void available(final Map<String
			throws TransportException {
		for (Map.Entry<String
			String name = e.getKey();
			String check;
			if (name.indexOf("/") >= 0)
				check = name;
			else
				check = "x/" + name;
			if (!Repository.isValidRefName(check))
				throw new TransportException("Invalid ref name received");
		}
