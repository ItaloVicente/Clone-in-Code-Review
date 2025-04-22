				if (element instanceof ReflogEntry) {
					final ReflogEntry entry = (ReflogEntry) element;
					RevCommit c = getCommit(entry);
					return c == null ? "" : c.getShortMessage(); //$NON-NLS-1$
				} else if (element instanceof IWorkbenchAdapter) {
					return ((IWorkbenchAdapter) element).getLabel(element);
				}
				return null;
