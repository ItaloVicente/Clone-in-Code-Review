    private void activateClient(IWorkbenchPart part) {
        if (part == this) {
            oleActivate();
            this.clientActive = true;
        }
    }

