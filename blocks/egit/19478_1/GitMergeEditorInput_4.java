				EditableRevision leftEditable;
				if (file != null)
					leftEditable = new ResourceEditableRevision(rev, file,
							runnableContext);
				else
					leftEditable = new LocationEditableRevision(rev, location,
							runnableContext);
