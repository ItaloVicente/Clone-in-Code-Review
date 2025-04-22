    public TestElement getTestElement(ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            return (TestElement) ((IStructuredSelection) selection)
                    .getFirstElement();
        }
        return null;
    }
