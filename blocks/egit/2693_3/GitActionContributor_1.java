
			IContributionItem fileGroup = findGroup(menu,
				ISynchronizePageConfiguration.FILE_GROUP);

			if (fileGroup != null) {
			    ModelSynchronizeParticipant msp =
		    		((ModelSynchronizeParticipant) getConfiguration()
			    			.getParticipant());

			    if (msp.hasCompareInputFor(element))
			    	menu.appendToGroup(fileGroup.getId(), openWorkingFileAction);
			}
