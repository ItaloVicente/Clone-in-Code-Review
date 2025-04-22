            }
        } else {
            if (quantizedHeight != ISizeProvider.INFINITE && quantizedHeight != 0) {
                result = Math.min(result + quantizedHeight/2, availableParallel);
                result = result - (result % quantizedHeight);
            }
            if (minHeight != ISizeProvider.INFINITE) {
                if (result < minHeight) {
