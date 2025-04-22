            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    boolean setEnabled(boolean enabled) {
        if (enabled != this.enabled) {
            this.enabled = enabled;
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    @Override
