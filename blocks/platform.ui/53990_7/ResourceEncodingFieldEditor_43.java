				if (!hasSameSeparateDerivedEncodings) {
					Preferences prefs = new ProjectScope((IProject) resource).getNode(ResourcesPlugin.PI_RESOURCES);
					boolean newValue = !getStoredSeparateDerivedEncodingsValue();
					if (newValue == DEFAULT_PREF_SEPARATE_DERIVED_ENCODINGS)
						prefs.remove(ResourcesPlugin.PREF_SEPARATE_DERIVED_ENCODINGS);
					else
						prefs.putBoolean(ResourcesPlugin.PREF_SEPARATE_DERIVED_ENCODINGS, newValue);
					prefs.flush();
				}
				return Status.OK_STATUS;
			} catch (CoreException e1) {// If there is an error return the
				IDEWorkbenchPlugin
						.log(
								IDEWorkbenchMessages.ResourceEncodingFieldEditor_ErrorStoringMessage,
								e1.getStatus());
				return e1.getStatus();
			} catch (BackingStoreException e2) {
				IDEWorkbenchPlugin.log(IDEWorkbenchMessages.ResourceEncodingFieldEditor_ErrorStoringMessage, e2);
				return new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, e2.getMessage(), e2);
