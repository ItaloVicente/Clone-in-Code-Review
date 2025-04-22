        createMementoState(memento);
    }

    /**
     * Creates an IMemento.
     */
    private void createMementoState(IMemento memento) {
        memento.putFloat("float", 0.50f);
        memento.putInteger("integer", 50);
        memento.putString("string", "50");

        IMemento child = memento.createChild("single");
        child.putInteger("id", 1);

        int count = 10;
        for (int nX = 0; nX < count; nX++) {
            child = memento.createChild("multiple");
            child.putInteger("id", nX);
        }
        memento.putInteger("multiple.count", count);
    }

    /**
     * Restore an IMemento.
     */
