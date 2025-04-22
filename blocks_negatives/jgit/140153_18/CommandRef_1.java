		} catch (InstantiationException e) {
			throw new RuntimeException(MessageFormat.format(CLIText.get().cannotCreateCommand, getName(), e));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(MessageFormat.format(CLIText.get().cannotCreateCommand, getName(), e));
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(MessageFormat.format(CLIText.get().cannotCreateCommand, getName(), e));
		} catch (InvocationTargetException e) {
			throw new RuntimeException(MessageFormat.format(CLIText.get().cannotCreateCommand, getName(), e));
