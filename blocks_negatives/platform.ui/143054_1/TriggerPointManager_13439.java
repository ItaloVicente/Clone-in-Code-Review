        IExtensionPoint point = getExtensionPointFilter();
        IExtension[] extensions = point.getExtensions();
        for (IExtension extension : extensions) {
            addExtension(tracker,
                    extension);
        }
    }
