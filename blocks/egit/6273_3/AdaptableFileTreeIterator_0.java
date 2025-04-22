	private IContainer findContainerFast(File currentFile) {
		String absFile = currentFile.getAbsolutePath();

		for(IProject prj : allProjects) {
			if(checkContainerMatch(prj, absFile))
				return prj;
		}

		if(checkContainerMatch(root, absFile))
			return root;

		return null;
	}

	private boolean checkContainerMatch(IContainer container, String absFile) {
		String absPrj = container.getLocation().toFile().getAbsolutePath();
		if(absPrj.length() == absFile.length()) {
			if(absPrj.equals(absFile))
				return true;
		} else if(absPrj.length() < absFile.length()) {
			char sepChar = absFile.charAt(absPrj.length());
			if(absFile.startsWith(absPrj) && (sepChar == '/' || sepChar == '\\')) {
				return true;
			}
		}
		return false;
	}

