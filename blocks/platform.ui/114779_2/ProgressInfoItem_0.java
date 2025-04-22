		if(taskString == null){
			taskString = "";  //$NON-NLS-1$
		}
		if (!taskString.isEmpty()) {
			taskString = Dialog.shortenText(taskString, link);
		}
