	}

	private EditorInputData readEditorInput(DataInputStream dataIn) throws IOException, WorkbenchException {

		String editorId = dataIn.readUTF();
		String factoryId = dataIn.readUTF();
		String xmlString = dataIn.readUTF();

		if (xmlString == null || xmlString.length() == 0) {
			return null;
		}

		StringReader reader = new StringReader(xmlString);

		XMLMemento memento = XMLMemento.createReadRoot(reader);

		IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(factoryId);

		if (factory != null) {
			IAdaptable adaptable = factory.createElement(memento);
			if (adaptable != null && (adaptable instanceof IEditorInput)) {
				return new EditorInputData(editorId, (IEditorInput) adaptable);
			}
		}

		return null;
	}

	private void writeEditorInput(DataOutputStream dataOut, EditorInputData editorInputData) throws IOException {
		dataOut.writeUTF(editorInputData.editorId);

		if (editorInputData.input != null) {
			XMLMemento memento = XMLMemento.createWriteRoot("IEditorInput");//$NON-NLS-1$

			IPersistableElement element = editorInputData.input.getPersistable();
			if (element != null) {
				element.saveState(memento);

				StringWriter writer = new StringWriter();
				memento.save(writer);
				writer.close();

				dataOut.writeUTF(element.getFactoryId());
				dataOut.writeUTF(writer.toString());
			}
		}
	}

	public static EditorInputData createEditorInputData(String editorId, IEditorInput input) {
		return new EditorInputData(editorId, input);
	}
