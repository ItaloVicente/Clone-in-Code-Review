		while (walk.next())
			outputDiff(System.out, walk.getPathString(),
				walk.getObjectId(0), walk.getFileMode(0),
				walk.getObjectId(1), walk.getFileMode(1));
	}

	protected void outputDiff(PrintStream out, String path,
			ObjectId id1, FileMode mode1, ObjectId id2, FileMode mode2) throws IOException {
		String name1 = "a/" + path;
		String name2 =  "b/" + path;
		out.println("diff --git " + name1 + " " + name2);
		boolean isNew=false;
		boolean isDelete=false;
		if (id1.equals(ObjectId.zeroId())) {
			out.println("new file mode " + mode2);
			isNew=true;
		} else if (id2.equals(ObjectId.zeroId())) {
			out.println("deleted file mode " + mode1);
			isDelete=true;
		} else if (!mode1.equals(mode2)) {
			out.println("old mode " + mode1);
			out.println("new mode " + mode2);
