					}
				}
			}
		}

		saveLinkingEnabled(memento);
	}

	private void saveLinkingEnabled(IMemento memento) {
		memento.putInteger(IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR, linkingEnabled ? 1 : 0);
	}

	@Override
