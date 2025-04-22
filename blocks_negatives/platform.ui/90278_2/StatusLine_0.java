		copyMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String text = fMessageLabel.getText();
				if (text != null && text.length() > 0) {
					text = LegacyActionTools.removeMnemonics(text);
					Clipboard cp = new Clipboard(e.display);
					cp.setContents(new Object[] { text },
							new Transfer[] { TextTransfer.getInstance() });
					cp.dispose();
				}
