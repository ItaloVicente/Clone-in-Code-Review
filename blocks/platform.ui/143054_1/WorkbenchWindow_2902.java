					Object saveResult = saveablesList.preCloseParts(saveableParts, addNonPartSources, true,
							WorkbenchWindow.this, WorkbenchWindow.this);
					return (saveResult != null);
				}

				@Override
				public boolean saveParts(Collection<MPart> dirtyParts, boolean confirm) {
					return saveParts(dirtyParts, confirm, false, false);
