			return true;
		}

		if (control instanceof Button || control instanceof ProgressBar
				|| control instanceof Sash || control instanceof Scale
				|| control instanceof Slider || control instanceof List
				|| control instanceof Combo || control instanceof Tree) {
			return true;
		}

		if (control instanceof Label || control instanceof Text) {
			return (control.getStyle() & SWT.WRAP) == 0;
		}


		return false;
	}

	private void computeHintOffset(Control control) {
