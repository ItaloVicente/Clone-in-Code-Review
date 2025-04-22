			result = new ObjectWalk(db);
		else if (argWalk != null)
			result = argWalk;
		else
		  result = argWalk = new RevWalk(db);
		result.setRewriteParents(false);
		return result;
