				IResourceDelta[] projectDeltas = delta.getAffectedChildren();
				for (int i = 0; i < projectDeltas.length; i++) {
					int kind = projectDeltas[i].getKind();
					boolean changed = (projectDeltas[i].getFlags() & (IResourceDelta.DESCRIPTION | IResourceDelta.OPEN)) != 0;
					if (kind != IResourceDelta.CHANGED || changed) {
						updateBuildActions(false);
						return;
					}
				}
