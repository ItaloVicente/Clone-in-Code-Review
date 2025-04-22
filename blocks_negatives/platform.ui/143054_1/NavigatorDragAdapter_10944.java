        if (actualLength < length) {
            String[] tempFileNames = fileNames;
            fileNames = new String[actualLength];
            for (int i = 0; i < actualLength; i++) {
				fileNames[i] = tempFileNames[i];
			}
        }
        event.data = fileNames;
    }

    /**
     * This implementation of {@link DragSourceListener#dragStart(DragSourceEvent)}
     * allows the drag to start if the current Navigator selection contains resources
     * that can be dragged.
     */
    @Override
