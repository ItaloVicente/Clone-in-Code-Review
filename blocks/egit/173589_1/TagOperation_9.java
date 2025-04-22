			command.setCredentialsProvider(getCredentialsProvider()).call();
			progress.worked(1);
		} catch (RefAlreadyExistsException e) {
			if (!RefUpdate.Result.NO_CHANGE.equals(e.getUpdateResult())) {
				throw new TeamException(MessageFormat.format(
						CoreText.TagOperation_taggingFailure, getName(),
						e.getMessage()), e);
			}
		} catch (GitAPIException e) {
			throw new TeamException(
					MessageFormat.format(CoreText.TagOperation_taggingFailure,
							getName(), e.getMessage()),
					e);
