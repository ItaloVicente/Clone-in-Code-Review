			if (commonBase.isEmpty()) {
				Set<ObjectId> tagTargets = new HashSet<ObjectId>();
				for (Ref ref : refs.values()) {
					if (ref.getPeeledObjectId() != null)
						tagTargets.add(ref.getPeeledObjectId());
					else if (ref.getObjectId() == null)
						continue;
					else if (ref.getName().startsWith(Constants.R_HEADS))
						tagTargets.add(ref.getObjectId());
				}
				pw.setTagTargets(tagTargets);
			}

