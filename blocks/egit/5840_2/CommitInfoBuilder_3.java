			if (found != null)
				result.add(ref);
			else
				for (ObjectId id : maybeCutOff)
					cutOff.addIfAbsent(id);

