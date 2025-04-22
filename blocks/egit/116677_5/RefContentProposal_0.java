			ObjectLoader loader = null;
			try {
				loader = reader.open(objectId);
			} catch (MissingObjectException e) {
				if (upstream) {
					return refName + '\n' + objectId.abbreviate(7).name()
							+ " - " //$NON-NLS-1$
							+ UIText.RefContentProposal_unknownRemoteObject;
				}
				throw e;
			}
