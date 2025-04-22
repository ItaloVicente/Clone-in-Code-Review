    private void deactivateClient(IWorkbenchPart part) {
        if (part == this && clientActive) {
            this.clientActive = false;
            this.oleActivated = false;
        }
    }

