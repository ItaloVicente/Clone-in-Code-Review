
	private void showUnsigned(Git git
		ObjectId id = git.getRepository().resolve(wantedTag);
		if (id != null && !ObjectId.zeroId().equals(id)) {
			try (RevWalk walk = new RevWalk(git.getRepository())) {
				showTag(walk.parseTag(id));
			}
		} else {
			throw die(
					MessageFormat.format(CLIText.get().tagNotFound
		}
	}

	private void showTag(RevTag tag) throws IOException {
		outw.println();
		outw.print(tag.getFullMessage());
	}

	private void writeVerification(String name
			SignatureVerification verification) throws IOException {
		showTag(tag);
		if (verification == null) {
			outw.println();
			return;
		}
		VerificationUtils.writeVerification(outw
				tag.getTaggerIdent());
	}
