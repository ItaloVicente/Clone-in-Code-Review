		for (Ref ref : remoteRefs) {
			String refName = ref.getName();
			String refHumanName = refName
					.substring(refName.lastIndexOf('/') + 1);
			syncRepoEnt.addRef(new SyncRefEntity(refHumanName, refName));
		}
