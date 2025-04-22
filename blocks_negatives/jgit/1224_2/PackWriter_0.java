	private void writeObjects(ProgressMonitor writeMonitor, PackOutputStream out)
			throws IOException {
		for (List<ObjectToPack> list : objectsLists) {
			for (ObjectToPack otp : list) {
				if (writeMonitor.isCancelled())
					throw new IOException(
							JGitText.get().packingCancelledDuringObjectsWriting);
				if (!otp.isWritten())
					writeObject(out, otp);
