        } else if (inString && value == '"') {
            boolean escaped = lastByte == '\\' && beforeLastByte != '\\';
            if (escaped) {
                done = false;
            } else {
                inString = false;
                done = true;
            }
