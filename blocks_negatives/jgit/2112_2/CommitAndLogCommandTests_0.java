		FileWriter wr = new FileWriter(new File(db.getDirectory(),
				Constants.MERGE_HEAD));
		wr.write(ObjectId.toString(db.resolve("refs/heads/master")));
		wr.close();
		wr = new FileWriter(new File(db.getDirectory(), Constants.MERGE_MSG));
		wr.write("merging");
		wr.close();
