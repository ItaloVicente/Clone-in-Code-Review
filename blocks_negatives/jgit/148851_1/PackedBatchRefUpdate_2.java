			}
		}

		while (addIdx < adds.size()) {
			ReceiveCommand cmd = adds.get(addIdx++);
			byName.remove(cmd.getRefName());
			b.add(peeledRef(walk, cmd));
		}
