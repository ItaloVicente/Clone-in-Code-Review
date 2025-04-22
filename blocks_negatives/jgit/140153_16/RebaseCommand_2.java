		} catch (RefAlreadyExistsException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (RefNotFoundException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (InvalidRefNameException e) {
			throw new JGitInternalException(e.getMessage(), e);
		} catch (CheckoutConflictException e) {
