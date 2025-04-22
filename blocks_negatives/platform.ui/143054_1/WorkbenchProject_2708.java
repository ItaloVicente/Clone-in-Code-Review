        IProject project = (IProject) resource;
        boolean isOpen = project.isOpen();
        String baseKey = isOpen ? IDE.SharedImages.IMG_OBJ_PROJECT
                : IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED;
        if (isOpen) {
            try {
