        if (i < len && value.charAt(i) == '-') {
            negative = true;
            ++i;
        }

        int result = 0;
        while (i < len) {
            int digit = Character.digit(value.charAt(i++), 10);
            if (digit < 0) {
                return result;
            }
            result = result * 10 + digit;
        }
        if (negative) {
            result = -result;
        }
        return result;
    }

    /**
     * Implements IProvider interface by supporting a number of
     * properties required for visual representation of markers
     * in the tasklist.
     */

    /**
     * Returns name if it is defined, or
     * blank string if not.
     */
    static String getResourceName(IMarker marker) {
        return marker.getResource().getName();
    }

    /**
     * Returns the creation time of the marker as a string.
     */
    static String getCreationTime(IMarker marker) {
        try {
            return DateFormat.getDateTimeInstance(DateFormat.LONG,
                    DateFormat.MEDIUM).format(
                    new Date(marker.getCreationTime()));
        } catch (CoreException e) {
            return null;
        }
    }
