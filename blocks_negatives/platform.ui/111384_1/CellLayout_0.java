            } else {
                return computeSize(composite, wHint, minimumSize.y, false);
            }
        } else {
            if (taller) {
                return computeSize(composite, minimumSize.x, hHint, false);
            } else {
                return minimumSize;
