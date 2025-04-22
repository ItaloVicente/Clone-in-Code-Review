            }
            return true;
        }
        return true;
    }

    /**
     * Sets the patterns to filter out for the receiver.
     */
    public void setPatterns(String[] newPatterns) {

        this.patterns = newPatterns;
        this.matchers = new StringMatcher[newPatterns.length];
        for (int i = 0; i < newPatterns.length; i++) {
            matchers[i] = new StringMatcher(newPatterns[i], true, false);
        }
    }
