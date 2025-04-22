			sb.append(name);
			if (r.isSymbolic()) {
				sb.append(": "); //$NON-NLS-1$
				sb.append(r.getLeaf().getName());
			}
			String description = GitLabelProvider.getRefDescription(r);
			if (description != null) {
				sb.append("\n"); //$NON-NLS-1$
				sb.append(description);
			}
			return sb.toString();
