			RevObject any;
			RevWalk walk = new RevWalk(node.getRepository());
			try {
				ObjectId id = node.getRepository().resolve(
						((Ref) node.getObject()).getName());
				if (id == null)
					return null;
				any = walk.parseAny(id);
			} catch (MissingObjectException e) {
				Activator.logError(e.getMessage(), e);
				return null;
			} catch (IOException e) {
				Activator.logError(e.getMessage(), e);
				return null;
			} finally {
				walk.release();
			}
			if (any instanceof RevTag)
