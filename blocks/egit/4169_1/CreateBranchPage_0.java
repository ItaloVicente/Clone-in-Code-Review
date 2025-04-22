
		try {
			if (myBaseRef.equals(myRepository.getFullBranch().toString())) {
				String sourceRef = myRepository.getConfig().getString(
						ConfigConstants.CONFIG_WORKFLOW_SECTION, null,
						ConfigConstants.CONFIG_KEY_DEFAULTSOURCEREF);
				if (sourceRef != null)
					branchCombo.setText(sourceRef);
			}
		} catch (IOException e1) {
		}
		
