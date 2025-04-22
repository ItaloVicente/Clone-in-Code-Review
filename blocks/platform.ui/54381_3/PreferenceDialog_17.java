					BusyIndicator.showWhile(getShell().getDisplay(), () -> {
						if (!isCurrentPageValid()) {
							handleError();
						} else if (!showPage((IPreferenceNode) selection)) {
							handleError();
						} else {
							lastSuccessfulNode = (IPreferenceNode) selection;
