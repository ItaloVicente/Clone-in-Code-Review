		if (firstSelected instanceof Ref) {
			RevObject any;
			try {
				any = rw
						.parseAny(repo.resolve(((Ref) firstSelected).getName()));
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
				return;
			}
			if (any instanceof RevTag)
				tag = (RevTag) any;
			else {
				setErrorMessage(UIText.CreateTagDialog_LightweightTagMessage);
				return;
			}
