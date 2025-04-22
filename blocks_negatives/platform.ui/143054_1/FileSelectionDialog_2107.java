                if (o instanceof FileSystemElement) {
                    return ((FileSystemElement) o).getFolders().getChildren(o);
                }
                return new Object[0];
            }
        };
    }

    /**
     * Initializes this dialog's controls.
     */
    private void initializeDialog() {
        if (getInitialElementSelections().isEmpty()) {
