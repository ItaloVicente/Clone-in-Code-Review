                flush();
            }
        }
    }

    /**
     * Returns the control whose size is being cached
     *
     * @return the control whose size is being cached, or null if this cache always returns (0,0)
     */
    public Control getControl() {
        return control;
    }

    /**
     * Flush the cache (should be called if the control's contents may have changed since the
     * last query)
     */
    public void flush() {
        flush(true);
    }

    public void flush(boolean recursive) {
        preferredSize = null;
