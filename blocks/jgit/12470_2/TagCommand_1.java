			if (!annotated) {
				if (message != null || tagger != null)
					throw new JGitInternalException(
							JGitText.get().messageAndTaggerNotAllowedInUnannotatedTags);
				return updateTagRef(id
			}

			TagBuilder newTag = new TagBuilder();
			newTag.setTag(name);
			newTag.setMessage(message);
			newTag.setTagger(tagger);
			newTag.setObjectId(id);

