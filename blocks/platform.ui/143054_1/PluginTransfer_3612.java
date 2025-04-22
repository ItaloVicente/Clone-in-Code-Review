		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			try (DataOutputStream dataOut = new DataOutputStream(out)) {
				dataOut.writeUTF(realData.getExtensionId());
				dataOut.writeInt(realData.getData().length);
				dataOut.write(realData.getData());
			}
			super.javaToNative(out.toByteArray(), transferData);
		} catch (IOException e) {
