			String id = contributionItem.getId();
			if(existance) {
				boolean removed = commands.remove(id);
				if(seenCommands.contains(id) && ! removed) {
					fail(name + " item duplicated in the context menu: " + id);
				}
			} else {
				assertTrue(name + " item should not be in the context menu", ! commands.contains(id));
			}
		}
