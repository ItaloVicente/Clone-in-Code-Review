    public void run(TestElement element) {
        if (fAll)
            element.getContainer().deleteChildren();
        else
            element.getContainer().deleteSomeChildren();
    }
