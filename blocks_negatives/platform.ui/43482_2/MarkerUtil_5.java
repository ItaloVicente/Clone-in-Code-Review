            } else {
                return location;
            }
        } else {
                return line
                        .format(new Object[] { Integer.toString(lineNumber) });
            } else {
                return lineAndLocation.format(new Object[] {
                        Integer.toString(lineNumber), location });
