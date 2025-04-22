					if (element != null) {
						list.add(element);
					}
				}
				viewer.setSelection(new StructuredSelection(list));
			}
		}
	}

	private void restoreLinkingEnabled() {
		Integer val = memento.getInteger(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR);
		if (val != null) {
			linkingEnabled = val.intValue() != 0;
		}
	}

	@Override
