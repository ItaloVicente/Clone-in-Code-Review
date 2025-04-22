				} catch (MissingObjectException e) {
					Activator.logError(e.getMessage(), e);
				} catch (IncorrectObjectTypeException e) {
					Activator.logError(e.getMessage(), e);
				} catch (IOException e) {
					Activator.logError(e.getMessage(), e);
