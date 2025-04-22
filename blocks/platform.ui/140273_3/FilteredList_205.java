							fTable.notifyListeners(SWT.Selection, new Event());
						}
					}
				} else {
					selectAndNotify(indicesToSelect);
				}
				readyForSelection = true;
			}
			return Status.OK_STATUS;
		}

