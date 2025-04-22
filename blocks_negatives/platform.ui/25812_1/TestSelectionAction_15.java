    /**
     * Overridden from Action.
     */
    public void run() {
        TestElement testElement = getTestElement(getBrowser().getViewer()
                .getSelection());
        if (testElement != null) {
            run(testElement);
        }
    }
