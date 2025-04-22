			for (RevCommit commit : call)
				result.add(commit);

		} catch (NoHeadException e) {
			Activator.logError(e.getMessage(), e);
		} catch (JGitInternalException e) {
			Activator.logError(e.getMessage(), e);
		} catch (MissingObjectException e) {
			Activator.logError(e.getMessage(), e);
		} catch (IncorrectObjectTypeException e) {
