			if (db == null)
				db = map.getRepository();
			else if (db != map.getRepository()) {
				super.setInput(null);
				db = null;
				setErrorMessage(UIText.GitHistoryPage_DifferentRepositoriesMessage);
				return false;
			}
