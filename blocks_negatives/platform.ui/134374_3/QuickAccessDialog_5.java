			keyAdapter = new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					int accelerator = SWTKeySupport.convertEventToUnmodifiedAccelerator(e);
					KeySequence keySequence = KeySequence.getInstance(SWTKeySupport
							.convertAcceleratorToKeyStroke(accelerator));
					TriggerSequence[] sequences = getInvokingCommandKeySequences();
					if (sequences == null)
						return;
					for (TriggerSequence sequence : sequences) {
						if (sequence.equals(keySequence)) {
							e.doit = false;
							contents.setShowAllMatches(!contents.getShowAllMatches());
							return;
						}
					}
				}
			};
