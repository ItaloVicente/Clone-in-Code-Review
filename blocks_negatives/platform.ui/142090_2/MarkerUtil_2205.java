            sb.append(path.segment(i));
        }
        return sb.toString();
    }

    /**
     * Returns the line number of the given marker.
     */
    static int getLineNumber(IMarker marker) {
        return marker.getAttribute(IMarker.LINE_NUMBER, -1);
    }

    /**
     * Returns the text for the location field.
     */
    static String getLocation(IMarker marker) {
        return marker.getAttribute(IMarker.LOCATION, "");//$NON-NLS-1$
    }

    /**
     * Returns the message attribute of the given marker,
     * or the empty string if the message attribute is not defined.
     */
    static String getMessage(IMarker marker) {
        return marker.getAttribute(IMarker.MESSAGE, "");//$NON-NLS-1$
    }

    /**
     * Returns the numeric value of the given string, which is assumed to represent a numeric value.
     *
     * @return <code>true</code> if numeric, <code>false</code> if not
     */
    static int getNumericValue(String value) {
        boolean negative = false;
        int i = 0;
        int len = value.length();

        if (i < len && value.charAt(i) == '#') {
