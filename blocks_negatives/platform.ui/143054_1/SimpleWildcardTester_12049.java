                return str.indexOf(pattern.substring(1, pattern.length() - 1)) >= 0;
            }
            return str.endsWith(pattern.substring(1));
            return str.startsWith(pattern.substring(0, pattern.length() - 1));
        } else {
            return str.equals(pattern);
        }
    }
