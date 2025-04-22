        if (primaryTimeout <= 0) {
            throw new IllegalArgumentException("Primary timeout must be greater than 0ms");
        }
        if (replicaTimeout <= 0) {
            throw new IllegalArgumentException("Replica timeout must be greater than 0ms");
        }

