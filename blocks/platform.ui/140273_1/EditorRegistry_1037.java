			memento.save(writer);
			writer.close();
			store.setValue(IPreferenceConstants.RESOURCES, writer.toString());
		} catch (IOException e) {
			MessageDialog.openError((Shell) null, "Saving Problems", //$NON-NLS-1$
					"Unable to save resource associations."); //$NON-NLS-1$
			return;
		}

		memento = XMLMemento.createWriteRoot(IWorkbenchConstants.TAG_EDITORS);
