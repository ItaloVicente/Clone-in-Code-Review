			if (gitModelWorkingTree.getChildren().length > 0)
				return gitModelWorkingTree;
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		return null;
