			PrePushHook hook;
			try {
				hook = LfsFactory.getInstance().getPrePushHook(repo
						outputStream);
			} catch (ConfigIllegalValueException e) {
				throw new TransportException(e.getMessage()
			}

