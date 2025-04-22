    /**
     * If the compatiblity bundle is loaded, then return the plugin object for
     * the extension on this runnable. Return null if the compatibility bundle
     * is not loaded or the plugin object cannot be created.
     */
    private Object getPluginForCompatibility() {
        IStatus status = new Status(IStatus.WARNING, PlatformUI.PLUGIN_ID, 0, message, null);
        WorkbenchPlugin.log(status);


        Bundle compatBundle = Platform.getBundle(PI_RUNTIME_COMPATIBILITY);
        if (compatBundle == null) {
			return null;
		}

        try {
            Class extensionClass = compatBundle.loadClass(EXTENSION_CLASS);
            Method getDescMethod = extensionClass.getDeclaredMethod(
                    GET_DESC_METHOD, new Class[0]);
            Object pluginDesc = getDescMethod.invoke(extension, new Object[0]);
            if (pluginDesc == null) {
				return null;
			}

            Class pluginDescClass = pluginDesc.getClass();
            Method getPluginMethod = pluginDescClass.getDeclaredMethod(
                    GET_PLUGIN_METHOD, new Class[0]);
            return getPluginMethod.invoke(pluginDesc, new Object[0]);
        } catch (ClassNotFoundException e) {
            handleException(e);
        } catch (IllegalAccessException e) {
            handleException(e);
        } catch (InvocationTargetException e) {
            handleException(e);
        } catch (NoSuchMethodException e) {
            handleException(e);
        }

        return null;
    }
