		if (myBaseRef != null
				&& (myBaseRef.startsWith(Constants.R_REMOTES) || myBaseRef
						.startsWith(Constants.R_TAGS))) {
			nameText.setText(myBaseRef
					.substring(myBaseRef.lastIndexOf('/') + 1));
			nameText.selectAll();
