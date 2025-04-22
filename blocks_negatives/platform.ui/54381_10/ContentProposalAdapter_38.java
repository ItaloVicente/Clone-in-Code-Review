				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						pendingDescriptionUpdate = true;
						try {
							Thread.sleep(POPUP_DELAY);
						} catch (InterruptedException e) {
						}
						if (!isValid()) {
							return;
						}
						getShell().getDisplay().syncExec(new Runnable() {
							@Override
							public void run() {
								IContentProposal p = getSelectedProposal();
								if (p != null) {
									String description = p.getDescription();
									if (description != null) {
										if (infoPopup == null) {
											infoPopup = new InfoPopupDialog(
													getShell());
											infoPopup.open();
											infoPopup
													.getShell()
													.addDisposeListener(
															new DisposeListener() {
																@Override
																public void widgetDisposed(
																		DisposeEvent event) {
																	infoPopup = null;
																}
															});
										}
										infoPopup.setContents(p
												.getDescription());
									} else if (infoPopup != null) {
										infoPopup.close();
									}
									pendingDescriptionUpdate = false;

