			command.setMessage(getMessage()).setTagger(getTagger());
			if (sign != null) {
				command.setSigned(sign.booleanValue());
			}
			CredentialsProvider provider = getCredentialsProvider();
			if (provider != null) {
				command.setCredentialsProvider(provider);
			}
			command.call();
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
