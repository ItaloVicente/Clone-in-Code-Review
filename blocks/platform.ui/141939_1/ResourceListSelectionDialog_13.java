					setShowDerived(showDerivedButton.getSelection());
					refresh(true);
				}
			});
			showDerivedButton.setSelection(getShowDerived());
		}

		applyDialogFont(dialogArea);
		return dialogArea;
	}

	public boolean getAllowUserToToggleDerived() {
		return allowUserToToggleDerived;
	}

	public void setAllowUserToToggleDerived(boolean allow) {
		allowUserToToggleDerived = allow;
	}

	private void filterResources(boolean force) {
		String oldPattern = force ? null : patternString;
		patternString = adjustPattern();
		if (!force && patternString.equals(oldPattern)) {
