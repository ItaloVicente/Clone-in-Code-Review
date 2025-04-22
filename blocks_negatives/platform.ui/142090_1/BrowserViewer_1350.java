    /**
     * Navigate to the next session history item. Convenience method that calls
     * the underlying SWT browser.
     *
     * @return <code>true</code> if the operation was successful and
     *         <code>false</code> otherwise
     * @exception SWTException
     *                <ul>
     *                <li>ERROR_THREAD_INVALID_ACCESS when called from the
     *                wrong thread</li>
     *                <li>ERROR_WIDGET_DISPOSED when the widget has been
     *                disposed</li>
     *                </ul>
     * @see #back
     */
    public boolean forward() {
        if (browser==null)
            return false;
        return browser.forward();
    }

    /**
     * Navigate to the previous session history item. Convenience method that
     * calls the underlying SWT browser.
     *
     * @return <code>true</code> if the operation was successful and
     *         <code>false</code> otherwise
     * @exception SWTException
     *                <ul>
     *                <li>ERROR_THREAD_INVALID_ACCESS when called from the
     *                wrong thread</li>
     *                <li>ERROR_WIDGET_DISPOSED when the widget has been
     *                disposed</li>
     *                </ul>
     * @see #forward
     */
    public boolean back() {
        if (browser==null)
            return false;
        return browser.back();
    }

    /**
     * Returns <code>true</code> if the receiver can navigate to the previous
     * session history item, and <code>false</code> otherwise. Convenience
     * method that calls the underlying SWT browser.
     *
     * @return the receiver's back command enabled state
     * @exception SWTException
     *                <ul>
     *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
     *                disposed</li>
     *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
     *                thread that created the receiver</li>
     *                </ul>
     * @see #back
     */
    public boolean isBackEnabled() {
        if (browser==null)
            return false;
        return browser.isBackEnabled();
    }

    /**
     * Returns <code>true</code> if the receiver can navigate to the next
     * session history item, and <code>false</code> otherwise. Convenience
     * method that calls the underlying SWT browser.
     *
     * @return the receiver's forward command enabled state
     * @exception SWTException
     *                <ul>
     *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
     *                disposed</li>
     *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
     *                thread that created the receiver</li>
     *                </ul>
     * @see #forward
     */
    public boolean isForwardEnabled() {
        if (browser==null)
            return false;
        return browser.isForwardEnabled();
    }

    /**
     * Stop any loading and rendering activity. Convenience method that calls
     * the underlying SWT browser.
     *
     * @exception SWTException
     *                <ul>
     *                <li>ERROR_THREAD_INVALID_ACCESS when called from the
     *                wrong thread</li>
     *                <li>ERROR_WIDGET_DISPOSED when the widget has been
     *                disposed</li>
     *                </ul>
     */
    public void stop() {
        if (browser!=null)
            browser.stop();
    }

    /**
     *
     */
    private boolean navigate(String url) {
        Trace.trace(Trace.FINER, "Navigate: " + url); //$NON-NLS-1$
        if (url != null && url.equals(getURL())) {
            refresh();
            return true;
        }
        if (browser!=null)
            return browser.setUrl(url, null, new String[] {"Cache-Control: no-cache"}); //$NON-NLS-1$
        return text.setUrl(url);
    }

    /**
     * Refresh the current page. Convenience method that calls the underlying
     * SWT browser.
     *
     * @exception SWTException
     *                <ul>
     *                <li>ERROR_THREAD_INVALID_ACCESS when called from the
     *                wrong thread</li>
     *                <li>ERROR_WIDGET_DISPOSED when the widget has been
     *                disposed</li>
     *                </ul>
     */
    public void refresh() {
        if (browser!=null)
            browser.refresh();
        else
            text.refresh();
		  try {
			  Thread.sleep(50);
		  } catch (Exception e) {
		  }
    }
