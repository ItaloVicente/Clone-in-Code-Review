	public String replaceVariable(String input) throws IOException {
		return input.replace("$" + type.getName()
	}

	public void addToEnv(Map<String
		env.put(type.getName()
	}

