								if (refText != refText.getDisplay()
										.getFocusControl()) {
									fillInPatchSet(result, null);
									return Status.CANCEL_STATUS;
								}
								fillInPatchSet(result, originalRefText);
								doAutoFill = false;
							} else {
								fillInPatchSet(result, null);
