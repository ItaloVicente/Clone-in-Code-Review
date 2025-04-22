		} catch (NumberFormatException e) {
			WorkbenchPlugin.log("Memento problem - invalid integer for key: " + key //$NON-NLS-1$
					+ " value: " + strValue, e); //$NON-NLS-1$
			return null;
		}
	}

	@Override
