			while (!trees.isEmpty()) {
				current.setId(inserter.insert(Constants.OBJ_TREE
				trees.pop();
				if (!trees.isEmpty())
					current = trees.peek();
			}
			inserter.flush();
			return current.getTreeId();
		} finally {
			inserter.release();
