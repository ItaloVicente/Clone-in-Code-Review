			if (useProtocolV2) {
				String symrefPart = symrefs.containsKey(ref.getName())
					? (" symref-target:" + symrefs.get(ref.getName()))
					: "";
				String peelPart = "";
				if (derefTags) {
					if (!ref.isPeeled() && repository != null) {
						ref = repository.peel(ref);
					}
					if (ref.getPeeledObjectId() != null) {
						peelPart = " peeled:" + ref.getPeeledObjectId().getName();
					}
				}
				writeOne(ref.getObjectId().getName() + " " + ref.getName() + symrefPart + peelPart + "\n");
				continue;
			}

