					BusyIndicator.showWhile(getShell().getDisplay(), new Runnable(){
						@Override
						public void run() {
							if (!isCurrentPageValid()) {
								handleError();
							} else if (!showPage((IPreferenceNode) selection)) {
								handleError();
							} else {
								lastSuccessfulNode = (IPreferenceNode) selection;
							}
