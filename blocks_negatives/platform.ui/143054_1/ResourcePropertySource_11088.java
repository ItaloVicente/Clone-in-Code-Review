        IPath resolvedLocation = resource.getLocation();
        if (resolvedLocation == null) {
            return true;
        }
        IPath rawLocation = resource.getRawLocation();
        if (resolvedLocation.equals(rawLocation)) {
