		} catch (NumberFormatException e) {
			WorkbenchPlugin.log("Memento problem - Invalid float for key: " //$NON-NLS-1$
					+ key + " value: " + strValue, e); //$NON-NLS-1$
			return null;
		}
	}
