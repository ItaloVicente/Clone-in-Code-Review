			return result = updateImpl(walk
				@Override
				Result execute(Result status) throws IOException {
					if (status == Result.NO_CHANGE)
						return status;
					return doUpdate(status);
				}
			});
