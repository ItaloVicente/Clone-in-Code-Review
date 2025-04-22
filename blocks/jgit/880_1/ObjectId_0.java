
	private void writeObject(ObjectOutputStream os)  throws IOException {
		os.writeInt(w1);
		os.writeInt(w2);
		os.writeInt(w3);
		os.writeInt(w4);
		os.writeInt(w5);
	}

        private void readObject(ObjectInputStream ois)  throws IOException {
		w1 = ois.readInt();
		w2 = ois.readInt();
		w3 = ois.readInt();
		w4 = ois.readInt();
		w5 = ois.readInt();
	}
