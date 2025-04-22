        IPath destinationLocation = getSpecifiedContainer().getLocation();
        if (destinationLocation != null) {
            return destinationLocation.isPrefixOf(sourcePath);
        }
        return false;
    }
