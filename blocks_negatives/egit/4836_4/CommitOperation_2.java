		} catch (NoHeadException e) {
			throw new TeamException(e.getLocalizedMessage(), e);
		} catch (NoMessageException e) {
			throw new TeamException(e.getLocalizedMessage(), e);
		} catch (UnmergedPathException e) {
			throw new TeamException(e.getLocalizedMessage(), e);
		} catch (ConcurrentRefUpdateException e) {
			throw new TeamException(CoreText.MergeOperation_InternalError, e);
