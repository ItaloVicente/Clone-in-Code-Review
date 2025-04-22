        StringReader reader = new StringReader(xmlString);

        XMLMemento memento = XMLMemento.createReadRoot(reader);

        IElementFactory factory = PlatformUI.getWorkbench().getElementFactory(
                factoryId);

        if (factory != null) {
            IAdaptable adaptable = factory.createElement(memento);
            if (adaptable != null && (adaptable instanceof IEditorInput)) {
                return new EditorInputData(editorId, (IEditorInput) adaptable);
            }
        }

        return null;
    }

    /**
     * Method writeEditorInput.
     * @param dataOut
     * @param editorInputData
     */
    private void writeEditorInput(DataOutputStream dataOut,
            EditorInputData editorInputData) throws IOException {
        dataOut.writeUTF(editorInputData.editorId);

        if (editorInputData.input != null) {

            IPersistableElement element = editorInputData.input
                    .getPersistable();
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

    public static EditorInputData createEditorInputData(String editorId,
            IEditorInput input) {
        return new EditorInputData(editorId, input);
    }
