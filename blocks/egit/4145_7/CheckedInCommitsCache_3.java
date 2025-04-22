		@Override
		public String toString() {
			StringBuilder change = new StringBuilder("Change("); //$NON-NLS-1$
			if ((kind & LEFT) != 0)
				change.append("OUTGOING "); //$NON-NLS-1$
			else // should be RIGHT
				change.append("INCOMING "); //$NON-NLS-1$
			if ((kind & ADDITION) != 0)
				change.append("ADDITION "); //$NON-NLS-1$
			else if ((kind & DELETION) != 0)
				change.append("DELETION "); //$NON-NLS-1$
			else // should be CHANGE
				change.append("CHANGE "); //$NON-NLS-1$

			change.append(name);
			change.append(";\n\tcurrent objectId: "); //$NON-NLS-1$
			change.append(getObjectId(objectId));
			change.append(";\n\tparent objectId: "); //$NON-NLS-1$
			change.append(getObjectId(remoteObjectId));
			change.append(";\n\tcurrent commit: "); //$NON-NLS-1$
			change.append(getObjectId(commitId));
			change.append(";\n\tparent commit: "); //$NON-NLS-1$
			change.append(remoteCommitId.toObjectId().getName());
			change.append("\n)"); //$NON-NLS-1$

			return change.toString();
		}

		private String getObjectId(AbbreviatedObjectId object) {
			if (object != null)
				return object.toObjectId().getName();
			else
				return ObjectId.zeroId().getName();
		}

