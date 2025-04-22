	private void addPersonIdent(StringBuilder d, PersonIdent ident,
			String label) {
		if (ident != null) {
			d.append(label).append(": "); //$NON-NLS-1$
			d.append(ident.getName().trim());
			d.append(" <").append(ident.getEmailAddress().trim()).append("> "); //$NON-NLS-1$ //$NON-NLS-2$
			d.append(fmt.format(ident.getWhen()));
			d.append(LF);
		}
	}

	private void addCommit(StringBuilder d, SWTCommit gitcommit, String label,
			List<GitCommitReference> hyperlinks) throws IOException {
		if (gitcommit != null) {
			d.append(label).append(": "); //$NON-NLS-1$
			gitcommit.parseBody();
			addLink(d, hyperlinks, gitcommit);
			d.append(" (").append(gitcommit.getShortMessage()).append(')'); //$NON-NLS-1$
			d.append(LF);
		}
	}

	private void addTag(StringBuilder d, String label, RevWalk walk, Ref tag,
			List<GitCommitReference> hyperlinks) throws IOException {
		if (tag != null) {
			d.append(label).append(": "); //$NON-NLS-1$
			RevCommit p = walk.parseCommit(tag.getObjectId());
			addLink(d, formatTagRef(tag), hyperlinks, p);
			d.append(LF);
		}
	}

