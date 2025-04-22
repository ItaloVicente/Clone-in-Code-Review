        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("The Document ID must not be null or empty.");
        }
        if (expiry < 0) {
            throw new IllegalArgumentException("The Document expiry must not be negative.");
        }

