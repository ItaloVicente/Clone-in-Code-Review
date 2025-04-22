		if (gitdir == null) {
			if (isBare) {
				String targetName = localName;
				if (!targetName.endsWith(Constants.DOT_GIT))
					targetName += Constants.DOT_GIT;
				gitdir = new File(targetName).getAbsolutePath();
			} else
				gitdir = new File(localName
						.getAbsolutePath();
		}
