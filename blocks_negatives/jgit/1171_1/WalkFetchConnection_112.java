
		final File o = local.toFile(id);
		if (tmp.renameTo(o))
			return;

		o.getParentFile().mkdir();
		if (tmp.renameTo(o))
			return;

		tmp.delete();
		if (local.hasObject(id))
			return;
		throw new ObjectWritingException(MessageFormat.format(JGitText.get().unableToStore, id.name()));
