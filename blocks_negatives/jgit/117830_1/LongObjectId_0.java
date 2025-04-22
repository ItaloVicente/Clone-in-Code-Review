
	private void writeObject(ObjectOutputStream os) throws IOException {
		os.writeLong(w1);
		os.writeLong(w2);
		os.writeLong(w3);
		os.writeLong(w4);
	}

	private void readObject(ObjectInputStream ois) throws IOException {
		w1 = ois.readLong();
		w2 = ois.readLong();
		w3 = ois.readLong();
		w4 = ois.readLong();
	}
