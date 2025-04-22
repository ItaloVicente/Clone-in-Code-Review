			String name = oldRef.getName();
			Ref cur = refs.get(name);
			if (cur != null && eq(cur, oldRef))
				return refs.remove(name, cur);
			else
				return false;
