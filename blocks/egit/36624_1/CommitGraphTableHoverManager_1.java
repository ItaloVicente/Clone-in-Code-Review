		if (information != null)
			setInformation(information.information, information.subjectArea);
		else
			setInformation(null, null);
	}

	private Information computeInformationForCommit(SWTCommit commit,
			ViewerCell cell, MouseEvent e) {
		final int columnIndex = cell.getColumnIndex();
		switch (columnIndex) {
		case 1:
			return computeInformationForRef(commit, cell, e);
		case 3:
			return computeInformationForDate(commit.getAuthorIdent(), cell);
		case 5:
			return computeInformationForDate(commit.getCommitterIdent(), cell);
		}
		return null;
	}

	private Information computeInformationForRef(SWTCommit commit,
			ViewerCell cell, MouseEvent e) {
		if (commit.getRefCount() == 0)
			return null;
		Rectangle itemBounds = cell.getBounds();
		int relativeX = e.x - itemBounds.x;
		for (int i = 0; i < commit.getRefCount(); i++) {
			Ref ref = commit.getRef(i);
			Point textSpan = renderer.getRefHSpan(ref);
			if ((textSpan != null)
					&& (relativeX >= textSpan.x && relativeX <= textSpan.y)) {

				String hoverText = getHoverText(ref, i, commit);
				int x = itemBounds.x + textSpan.x;
				int width = textSpan.y - textSpan.x;
				Rectangle rectangle = new Rectangle(x, itemBounds.y, width,
						itemBounds.height);
				return new Information(hoverText, rectangle);
			}
		}
		return null;
	}

	private Information computeInformationForDate(PersonIdent ident,
			ViewerCell cell) {
		String formattedDate = dateFormatter.formatDate(ident);
		return new Information(formattedDate, cell.getBounds());
