            changeFontButton.addSelectionListener(new SelectionAdapter() {
                @Override
				public void widgetSelected(SelectionEvent event) {
                    FontDialog fontDialog = new FontDialog(changeFontButton
                            .getShell());
                    if (chosenFont != null) {
						fontDialog.setFontList(chosenFont);
