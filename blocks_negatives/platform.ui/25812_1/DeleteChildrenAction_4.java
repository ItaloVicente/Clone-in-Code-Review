    public void run(TestElement element) {
        if (fAll)
            element.deleteChildren();
        else
            element.deleteSomeChildren();
    }
