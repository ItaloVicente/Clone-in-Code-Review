					}
				}
			}
			Iterator iter = appearList.iterator();
			IExtensionDelta extDelta = null;
			while (iter.hasNext()) {
				extDelta = (IExtensionDelta) iter.next();
				extPt = extDelta.getExtensionPoint();
				ext = extDelta.getExtension();
				asyncAppear(display, extPt, ext);
			}

			resetCurrentPerspective(display);
		} finally {
			changeList.clear();
		}

	}

	private void asyncAppear(Display display, final IExtensionPoint extpt, final IExtension ext) {
		Runnable run = () -> appear(extpt, ext);
		display.syncExec(run);
	}

	private void appear(IExtensionPoint extPt, IExtension ext) {
		String name = extPt.getSimpleIdentifier();
		if (name.equalsIgnoreCase(IWorkbenchRegistryConstants.PL_FONT_DEFINITIONS)) {
			loadFontDefinitions(ext);
			return;
		}
		if (name.equalsIgnoreCase(IWorkbenchRegistryConstants.PL_THEMES)) {
			loadThemes(ext);
			return;
		}
	}

	private void loadFontDefinitions(IExtension ext) {
		ThemeRegistryReader reader = new ThemeRegistryReader();
		reader.setRegistry((ThemeRegistry) WorkbenchPlugin.getDefault().getThemeRegistry());
