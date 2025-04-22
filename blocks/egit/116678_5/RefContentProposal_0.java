		if (author != null) {
			sb.append(" "); //$NON-NLS-1$
			sb.append(UIText.RefContentProposal_by);
			sb.append(" "); //$NON-NLS-1$
			sb.append(author.getName());
			sb.append("\n"); //$NON-NLS-1$
			sb.append(author.getWhen());
		}
