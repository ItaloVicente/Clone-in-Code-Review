	 * Loads the dialog settings for this plug-in. The default implementation first
	 * looks for a standard named file in the plug-in's read/write state area; if no
	 * such file exists, default product dialog settings directory (specified by
	 * org.eclipse.ui/default_dialog_settings_rootUrl property) is checked to see if
	 * there is a file with default plug-in dialog settings exists; if no such file
	 * exists, the plug-in's install directory is checked to see if one was
	 * installed with some default settings; if no file is found in either place, a
	 * new empty dialog settings is created. If a problem occurs, an empty settings
	 * is silently used.
