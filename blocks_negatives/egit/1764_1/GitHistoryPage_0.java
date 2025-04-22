			final IResource[] array = ((HistoryPageInput) object).getItems();
			if (array.length == 0)
				return false;
			for (final IResource r : array) {
				if (!typeOk(r))
					return false;
			}
