        long step;
        if (attempt <= 0) { //safeguard against underflow
            step = 0;
        } else if (attempt >= 32) { //safeguard against overflow
            step = Long.MAX_VALUE;
        } else {
            step = (1 << (attempt - 1));
        }
        long calc = Math.round(step * growBy);
