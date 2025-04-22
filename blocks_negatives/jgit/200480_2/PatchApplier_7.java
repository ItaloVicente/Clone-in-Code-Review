			return new ContentStreamLoader(supp, finalSize);
		}
		default:
			throw new UnsupportedOperationException(MessageFormat.format(
					JGitText.get().applyBinaryPatchTypeNotSupported,
					hunk.getType().name()));
