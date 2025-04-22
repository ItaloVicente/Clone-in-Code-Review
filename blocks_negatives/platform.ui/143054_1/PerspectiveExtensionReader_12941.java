                return processExtension(element);
            }
            return true;
        }
        return false;
    }

    /**
     * Sets the tags to include.  All others are ignored.
     *
     * @param tags the tags to include
     */
    public void setIncludeOnlyTags(String[] tags) {
