            calc = 0;
        } else if (powersOf == 2) {
            calc = calculatePowerOfTwo(attempt);
        } else {
            calc = calculateAlternatePower(attempt);
        }

        return applyBounds(calc);
    }

    protected long calculateAlternatePower(long attempt) {
        double step = Math.pow(this.powersOf, attempt - 1); //attempt > 0
        return Math.round(step * growBy);
    }

    protected long calculatePowerOfTwo(long attempt) {
        long step;
        if (attempt >= 64) { //safeguard against overflow in the bitshift operation
