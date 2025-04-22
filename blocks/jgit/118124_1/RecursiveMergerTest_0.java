		switch (worktreeState) {
		case Missing:
			new File(db.getWorkTree()
			break;
		case DifferentFromHeadAndOther:
			write(new File(db.getWorkTree()
					Integer.toString(counter++));
			break;
		case SameAsHead:
			try (FileOutputStream fos = new FileOutputStream(
					new File(db.getWorkTree()
				db.newObjectReader().open(contentId(Constants.HEAD
						.copyTo(fos);
