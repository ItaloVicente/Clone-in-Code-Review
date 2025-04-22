			if (!isGroupNameValid(name)) {
				toDelete.add(new RepositoryGroup(groupId, name));
				Activator.logWarning(MessageFormat.format(
						UIText.RepositoryGroups_LoadPreferencesInvalidName,
						name), null);
				continue;
			}
