				for (Ref ref : repo.getAllRefs().values()) {
					if (ref.getObjectId() != null)
						r.add(ref.getObjectId());
					if (ref.getPeeledObjectId() != null)
						r.add(ref.getPeeledObjectId());
				}
