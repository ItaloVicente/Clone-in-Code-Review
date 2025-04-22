		FileOutputStream fos = null;
		ObjectId bloblId;

		try {
			switch (worktreeState) {
			case Missing:
				new File(db.getWorkTree(), path).delete();
				break;
			case DifferentFromHeadAndOther:
				write(new File(db.getWorkTree(), path),
						Integer.toString(counter++));
				break;
			case SameAsHead:
				bloblId = contentId(Constants.HEAD, path);
				fos = new FileOutputStream(new File(db.getWorkTree(), path));
				db.newObjectReader().open(bloblId).copyTo(fos);
				break;
			case SameAsOther:
				bloblId = contentId(other, path);
				fos = new FileOutputStream(new File(db.getWorkTree(), path));
				db.newObjectReader().open(bloblId).copyTo(fos);
				break;
			case Bare:
				if (db.isBare())
					return;
				File workTreeFile = db.getWorkTree();
				db.getConfig().setBoolean("core", null, "bare", true);
				db.getDirectory().renameTo(new File(workTreeFile, "test.git"));
				db = new FileRepository(new File(workTreeFile, "test.git"));
				db_t = new TestRepository<>(db);
