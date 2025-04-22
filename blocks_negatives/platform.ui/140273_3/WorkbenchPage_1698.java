
    public boolean isPartVisible(IWorkbenchPartReference reference) {
        IWorkbenchPart part = reference.getPart(false);
        if (part == null) {
            return false;
        }

        return isPartVisible(part);
    }
