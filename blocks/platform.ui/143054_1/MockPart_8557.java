				URL installURL = plugin.getEntry("/"); //$NON-NLS-1$
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

	public void addPropertyListener(IPropertyListener listener) {
		addListenerObject(listener);
	}

	public void createPartControl(Composite parent) {
		callTrace.add("createPartControl");

		parent.addDisposeListener(disposeListener);
	}

	public void dispose() {
		callTrace.add("dispose");
	}

	public Image getTitleImage() {
		return titleImage;
	}

	public void removePropertyListener(IPropertyListener listener) {
		removeListenerObject(listener);
	}

	public void setFocus() {
		callTrace.add("setFocus");
	}
