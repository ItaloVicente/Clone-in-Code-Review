			if (part.getCurSharedRef().getTags()
					.contains(IPresentationEngine.NO_CLOSE)
					|| part.getTags().contains(IPresentationEngine.NO_CLOSE)) {
				return false;
			}
			return part.getCurSharedRef().isCloseable();
