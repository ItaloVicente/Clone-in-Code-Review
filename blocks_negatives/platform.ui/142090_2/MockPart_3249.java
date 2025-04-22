                URL fullPathString = new URL(installURL, strIcon);
                ImageDescriptor imageDesc = ImageDescriptor
                        .createFromURL(fullPathString);
                titleImage = imageDesc.createImage();
            } catch (MalformedURLException e) {
            }
        }
    }

    protected IConfigurationElement getConfig() {
        return config;
    }

    protected Object getData() {
        return data;
    }

    public void widgetDisposed() {
    	callTrace.add("widgetDisposed");
    }

    /**
     * @see IWorkbenchPart#addPropertyListener(IPropertyListener)
     */
    public void addPropertyListener(IPropertyListener listener) {
        addListenerObject(listener);
    }

    /**
     * @see IWorkbenchPart#createPartControl(Composite)
     */
    public void createPartControl(Composite parent) {
        callTrace.add("createPartControl");

        parent.addDisposeListener(disposeListener);
    }

    /**
     * @see IWorkbenchPart#dispose()
     */
    public void dispose() {
        callTrace.add("dispose");
    }

    /**
     * @see IWorkbenchPart#getTitleImage()
     */
    public Image getTitleImage() {
        return titleImage;
    }

    /**
     * @see IWorkbenchPart#removePropertyListener(IPropertyListener)
     */
    public void removePropertyListener(IPropertyListener listener) {
        removeListenerObject(listener);
    }

    /**
     * @see IWorkbenchPart#setFocus()
     */
    public void setFocus() {
        callTrace.add("setFocus");
    }
