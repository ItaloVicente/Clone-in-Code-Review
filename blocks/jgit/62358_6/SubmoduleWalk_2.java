		}
	}

	private String getModuleName(String modulePath) {
		String name = pathToName != null ? pathToName.get(modulePath) : null;
		return name != null ? name : modulePath;
