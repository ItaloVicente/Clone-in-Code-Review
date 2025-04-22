        fViewer = viewer;
        TestElement oldElement = (TestElement) oldInput;
        if (oldElement != null) {
            oldElement.getModel().removeListener(this);
        }
        TestElement newElement = (TestElement) newInput;
        if (newElement != null) {
            newElement.getModel().addListener(this);
        }
    }
