				private void removeSaveOnCloseNotNeededParts(List<IWorkbenchPart> parts) {
					for (Iterator<IWorkbenchPart> it = parts.iterator(); it.hasNext();) {
						IWorkbenchPart part = it.next();
						ISaveablePart saveable = SaveableHelper.getSaveable(part);
						if (saveable == null || !saveable.isSaveOnCloseNeeded()) {
							it.remove();
						}
					}
				}

