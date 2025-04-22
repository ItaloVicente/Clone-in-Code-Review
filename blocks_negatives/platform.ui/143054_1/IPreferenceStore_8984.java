    /**
     * <p>
     * Adds a property change listener to this preference store.
     * </p>
     * <p>
     * <b>Note</b> The types of the oldValue and newValue of the
     * generated PropertyChangeEvent are determined by whether
     * or not the typed API in IPreferenceStore was called.
     * If values are changed via setValue(name,type) the
     * values in the PropertyChangedEvent will be of that type.
     * If they are set using a non typed API (i.e. #setToDefault
     * or using the OSGI Preferences) the values will be unconverted
     * Strings.
     * </p>
     * <p>
     * A listener will be called in the same Thread
     * that it is invoked in. Any Thread dependant listeners (such as
     * those who update an SWT widget) will need to update in the
     * correct Thread. In the case of an SWT update you can update
     * using Display#syncExec(Runnable) or Display#asyncExec(Runnable).
     * </p>
     * <p>
     * Likewise any application that updates an IPreferenceStore
     * from a Thread other than the UI Thread should be aware of
     * any listeners that require an update in the UI Thread.
     * </p>
     *
     * @param listener a property change listener
     * @see org.eclipse.jface.util.PropertyChangeEvent
     * @see #setToDefault(String)
     * @see #setValue(String, boolean)
     * @see #setValue(String, double)
     * @see #setValue(String, float)
     * @see #setValue(String, int)
     * @see #setValue(String, long)
     * @see #setValue(String, String)
     */
