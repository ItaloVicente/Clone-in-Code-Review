		}

		return false;
	}

	protected void internalSaveWidgetValues() {
	}

	protected Object[] queryResourceTypesToExport() {

		TypeFilteringDialog dialog = new TypeFilteringDialog(getContainer().getShell(), getTypesToExport());

		dialog.open();

		return dialog.getResult();
	}

	protected void restoreResourceSpecificationWidgetValues() {
	}

	@Override
