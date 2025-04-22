	private static class TagWrapper {
		RevTag annotatedTag;

		Ref lightweightTag;

		TagWrapper(RevTag t) {
			annotatedTag = t;
			lightweightTag = null;
		}

		TagWrapper(Ref l) {
			annotatedTag = null;
			lightweightTag = l;
		}

		public String getName() {
			if (annotatedTag != null)
				return annotatedTag.getTagName();
			return lightweightTag.getName().replaceFirst("^" + Constants.R_TAGS, //$NON-NLS-1$
					""); //$NON-NLS-1$
		}

		public ObjectId getId() {
			if (annotatedTag != null)
				return annotatedTag.getObject();
			return lightweightTag.getObjectId();
		}

		public String getMessage() {
			if (annotatedTag != null)
				return annotatedTag.getFullMessage();
			return null;
		}
	}

