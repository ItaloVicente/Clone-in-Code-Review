    /**
     * Sets the object that owns the properties shown in this page.
     * The page is expected to store this object and provide it if
     * <code>getElement</code> is called.
     * <p> As of Eclipse 3.2 the org.eclipse.ui.propertyPages extension
     * point now supports non IAdaptable inputs. An input
     * that is not an IAdaptable will be wrapped in an
     * IAdaptable by the workbench before it is forwarded
     * to this method.
     * </p>
     * @see PropertyDialogAction
     *
     * @param element the object that owns the properties shown in this page
     */
    void setElement(IAdaptable element);
