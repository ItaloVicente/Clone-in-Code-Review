		} catch (MissingObjectException e) {
			Activator.error(e.getMessage(), e);
		} catch (IncorrectObjectTypeException e) {
			Activator.error(e.getMessage(), e);
		} catch (CorruptObjectException e) {
			Activator.error(e.getMessage(), e);
