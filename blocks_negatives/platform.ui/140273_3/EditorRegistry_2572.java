        for (int i = 0; i < tempArray.length; i++) {
            array[i] = (IEditorDescriptor) tempArray[i];
        }
        return array;
    }

    /**
     * Return the editors loaded from plugins.
     *
     * @return the sorted array of editors declared in plugins
     */
