	public void writeObjects(PackOutputStream out
			boolean clearList) throws IOException {
		for (int i = 0; i < list.size(); i++) {
			out.writeObject(list.get(i));
			if (clearList) {
				list.set(i
			}
		}
