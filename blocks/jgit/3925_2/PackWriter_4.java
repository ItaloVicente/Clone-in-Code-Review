	private void writeObjectAndDeltaChildren(PackOutputStream out
			ObjectToPack otp) throws IOException {
		writeObject(out

		ObjectToPack child = otp.deltaChild;
		while (child != null) {
			writeObjectAndDeltaChildren(out
			child = child.deltaNext;
		}
	}

