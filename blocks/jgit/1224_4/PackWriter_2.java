	private void writeObjects(PackOutputStream out) throws IOException {
		if (reuseSupport != null) {
			for (List<ObjectToPack> list : objectsLists)
				reuseSupport.writeObjects(out
		} else {
			for (List<ObjectToPack> list : objectsLists) {
				for (ObjectToPack otp : list)
					out.writeObject(otp);
