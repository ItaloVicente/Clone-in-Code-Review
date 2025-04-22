        String destinationValue = getDestinationValue();
        if (destinationValue.length() == 0) {
            setMessage(destinationEmptyMessage());
            return false;
        }

        String conflictingContainer = getConflictingContainerNameFor(destinationValue);
        if (conflictingContainer == null) {
