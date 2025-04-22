				} else if (isUniqueMatch(sequenceAfterKeyStroke, staticContext)) {
					final ParameterizedCommand cmd = getExecutableMatches(sequenceAfterKeyStroke, context).iterator().next()
							.getParameterizedCommand();
					try {
						return executeCommand(cmd, event) || !sequenceBeforeKeyStroke.isEmpty();
					} catch (final CommandException e) {
						return true;
					}

				} else if ((keyAssistDialog != null)
						&& (keyAssistDialog.getShell() != null)
						&& ((event.keyCode == SWT.ARROW_DOWN) || (event.keyCode == SWT.ARROW_UP)
								|| (event.keyCode == SWT.ARROW_LEFT)
								|| (event.keyCode == SWT.ARROW_RIGHT) || (event.keyCode == SWT.CR)
								|| (event.keyCode == SWT.PAGE_UP) || (event.keyCode == SWT.PAGE_DOWN))) {
					return false;
