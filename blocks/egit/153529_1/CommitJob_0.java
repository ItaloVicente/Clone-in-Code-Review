			private String createTitle() {
				StringBuilder b = new StringBuilder();

				b.append(UIText.CommitJob_AbortedByHook);
				b.append(" \""); //$NON-NLS-1$
				b.append(cause.getHookName());
				b.append("\""); //$NON-NLS-1$

				return b.toString();
			}

