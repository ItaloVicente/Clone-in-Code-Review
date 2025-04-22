            if (nextByte == '\"' && !escapedInString) {
                inString = false;
            }

            if (nextByte == '\\' && !escapedInString) {
                escapedInString = true;
            } else if (escapedInString) {
                escapedInString = false;
