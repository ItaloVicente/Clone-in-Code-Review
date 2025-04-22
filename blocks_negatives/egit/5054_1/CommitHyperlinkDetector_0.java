		List<IHyperlink> hyperlinks = new ArrayList<IHyperlink>();
		Shell shell = textViewer.getTextWidget().getShell();
		for (String potentialId : words) {
			String foundId = null;
			int foundOffset = 0;
			if (ObjectId.isId(potentialId)) {
				foundId = potentialId;
				foundOffset = offset;
			} else if (potentialId.length() > Constants.OBJECT_ID_STRING_LENGTH) {
				String potentialIdAtBeginning = potentialId.substring(0,
						Constants.OBJECT_ID_STRING_LENGTH);
				if (ObjectId.isId(potentialIdAtBeginning)) {
					foundId = potentialIdAtBeginning;
					foundOffset = offset;
				} else {
					String potentialIdAtEnd = potentialId.substring(potentialId
							.length() - Constants.OBJECT_ID_STRING_LENGTH);
					if (ObjectId.isId(potentialIdAtEnd)) {
						foundId = potentialIdAtEnd;
						foundOffset = potentialId.length()
								- Constants.OBJECT_ID_STRING_LENGTH;
					}

				}
			}
			if (foundId != null) {
				CommitHyperlink hyperlink = new CommitHyperlink(new Region(
						foundOffset, Constants.OBJECT_ID_STRING_LENGTH),
						foundId, shell);
				hyperlinks.add(hyperlink);
			}
			offset += potentialId.length() + 1;
