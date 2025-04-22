        Object target = determineTarget(event);

        int oldLocation = currentLocation;
        currentLocation = determineLocation(event);
        setFeedback(event, currentLocation);

        if (target != currentTarget || currentLocation != oldLocation) {
            currentTarget = target;
            doDropValidation(event);
        }
    }

    @Override
