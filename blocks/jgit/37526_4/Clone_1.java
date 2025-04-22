			outw.println(MessageFormat.format(CLIText.get().cloningInto
					command.getDirectory().getPath()));
			db = command.call().getRepository();
			if (db.resolve(Constants.HEAD) == null)
				outw.println(CLIText.get().clonedEmptyRepository);
		} catch (InvalidRemoteException e) {
			throw die(MessageFormat.format(CLIText.get().doesNotExist
					sourceUri));
