	public void writeObjects(PackOutputStream out
			throws IOException {
		for (ObjectToPack otp : list)
			out.writeObject(otp);
	}

