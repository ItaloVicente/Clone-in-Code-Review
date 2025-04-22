			result = updateResult;
			switch (updateResult) {
			case NEW:
			case FORCED:
			case NO_CHANGE:
			default:
				throw new TeamException(NLS.bind(CoreText.TagOperation_taggingFailure,
						tag.getTag(), updateResult));
			}
		} catch (IOException e) {
			throw new TeamException(NLS.bind(CoreText.TagOperation_taggingFailure,
					tag.getTag(), e.getMessage()), e);
		}
