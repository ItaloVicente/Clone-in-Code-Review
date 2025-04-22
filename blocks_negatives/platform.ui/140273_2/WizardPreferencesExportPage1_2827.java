        String destinationText = super.getDestinationValue();

        if (destinationText.length() != 0
                && !destinationText.endsWith(File.separator)) {
            int dotIndex = destinationText.lastIndexOf('.');
            if (dotIndex != -1) {
