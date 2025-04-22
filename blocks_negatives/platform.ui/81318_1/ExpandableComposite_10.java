			textLabel.addTraverseListener(new TraverseListener() {
				@Override
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_MNEMONIC) {
						if (!isVisible() || !isEnabled())
							return;
						if (FormUtil.mnemonicMatch(getText(), e.character)) {
							e.doit = false;
							if (!isFixedStyle()) {
							    programmaticToggleState();
							}
							setFocus();
