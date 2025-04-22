		} catch (AccessControlException e) {
			LOG.warn(
					"FS.readPipe() isn't allowed for command '{}'. Working directory: '{}'. Required permission: {}"
					command
		} catch (SecurityException e) {
			LOG.warn(
					"FS.readPipe() isn't allowed for command '{}'. Working directory: '{}'"
					command
