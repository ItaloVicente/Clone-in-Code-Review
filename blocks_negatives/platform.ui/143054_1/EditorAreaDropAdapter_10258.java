        /* Open Editor for generic IEditorInput */
        if (EditorInputTransfer.getInstance().isSupportedType(
                event.currentDataType)) {
            /* event.data is an array of EditorInputData, which contains an IEditorInput and
             * the corresponding editorId */
            Assert.isTrue(event.data instanceof EditorInputTransfer.EditorInputData[]);
