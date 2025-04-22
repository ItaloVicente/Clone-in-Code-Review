		Patch patch = new Patch();
		try (InputStream inStream = in) {
			patch.parse(inStream);
			if (!patch.getErrors().isEmpty()) {
				throw new PatchFormatException(patch.getErrors());
			}
		} catch (IOException e) {
			throw new PatchApplyException(MessageFormat.format(
					JGitText.get().patchApplyException
		}
