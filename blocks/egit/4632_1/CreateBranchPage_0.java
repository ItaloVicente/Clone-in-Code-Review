		if (myBaseRef != null) {
			String shortened;
			if (myBaseRef.startsWith(Constants.R_REMOTES)) {
				shortened = myBaseRef.substring(Constants.R_REMOTES.length());
				int postSlash = shortened.indexOf('/') + 1;
				if (postSlash > 0 && postSlash < shortened.length())
					shortened = shortened.substring(postSlash);
			} else if (myBaseRef.startsWith(Constants.R_TAGS))
				shortened = myBaseRef.substring(Constants.R_TAGS.length());
			else
				shortened = null;
			if (shortened != null) {
				nameText.setText(shortened);
				nameText.selectAll();
			} else
				setPageComplete(false);
