		if (!(data instanceof EditorInputData[])) {
			return;
		}

		EditorInputData[] editorInputs = (EditorInputData[]) data;

		int editorInputCount = editorInputs.length;

		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(out);

			dataOut.writeInt(editorInputCount);

			for (EditorInputData editorInput : editorInputs) {
				writeEditorInput(dataOut, editorInput);
			}

			dataOut.close();
			out.close();
			byte[] bytes = out.toByteArray();
			super.javaToNative(bytes, transferData);
		} catch (IOException e) {
		}
	}

	@Override
