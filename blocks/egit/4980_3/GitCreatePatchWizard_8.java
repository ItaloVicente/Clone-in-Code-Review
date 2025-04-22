
		DiffHeaderFormat getSelectedHeaderFormat() {
			IStructuredSelection selection = (IStructuredSelection) formatCombo
					.getSelection();
			return (DiffHeaderFormat) selection.getFirstElement();
		}
