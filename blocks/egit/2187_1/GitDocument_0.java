			if (tw == null) {
				setResolved(null, null, null, ""); //$NON-NLS-1$
				String msg = NLS
						.bind(UIText.GitDocument_errorLoadTree, new Object[] {
								treeId, baseline, resource, repository });
				Activator.logError(msg, new Throwable());
				setResolved(null, null, null, ""); //$NON-NLS-1$
				return;
			}
