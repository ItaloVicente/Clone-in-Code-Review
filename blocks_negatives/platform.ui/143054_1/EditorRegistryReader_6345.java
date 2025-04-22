        return compareList(this.deletedEditors, mapping.deletedEditors);
    }

    /**
     * Compare the editor ids from both lists and return true if they
     * are equals.
     */
	private boolean compareList(List<IEditorDescriptor> l1, List<IEditorDescriptor> l2) {
        if (l1.size() != l2.size()) {
			return false;
		}

		Iterator<IEditorDescriptor> i1 = l1.iterator();
		Iterator<IEditorDescriptor> i2 = l2.iterator();
        while (i1.hasNext() && i2.hasNext()) {
            Object o1 = i1.next();
            Object o2 = i2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2))) {
				return false;
			}
        }
        return true;
    }
