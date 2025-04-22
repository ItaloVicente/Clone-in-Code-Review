	@Option(name = "--delete-force"
			"-D" }
	public void deleteForce(List<String> names) {
		if (names.isEmpty()) {
			throw die(CLIText.get().branchNameRequired);
		}
		deleteForce = names;
	}

	@Option(name = "--create-force"
			"-f" }
	public void createForce(List<String> branchAndStartPoint) {
		createForce = true;
		if (branchAndStartPoint.isEmpty()) {
			throw die(CLIText.get().branchNameRequired);
		}
		if (branchAndStartPoint.size() > 2) {
			throw die(CLIText.get().tooManyRefsGiven);
		}
		if (branchAndStartPoint.size() == 1) {
			branch = branchAndStartPoint.get(0);
		} else {
			branch = branchAndStartPoint.get(0);
			otherBranch = branchAndStartPoint.get(1);
		}
	}

	@Option(name = "--move"
			"-m" }
	public void moveRename(List<String> currentAndNew) {
		rename = true;
		if (currentAndNew.isEmpty()) {
			throw die(CLIText.get().branchNameRequired);
		}
		if (currentAndNew.size() > 2) {
			throw die(CLIText.get().tooManyRefsGiven);
		}
		if (currentAndNew.size() == 1) {
			branch = currentAndNew.get(0);
		} else {
			branch = currentAndNew.get(0);
			otherBranch = currentAndNew.get(1);
		}
	}
