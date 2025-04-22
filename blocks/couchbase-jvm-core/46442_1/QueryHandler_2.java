
        sectionDone = false;
        return newState;
    }

    private void sectionDone() {
        this.sectionDone = true;
        responseContent.discardReadBytes();
