		IPreferenceNode[] prefNodes = super.getSubNodes();
		int size = prefNodes.length;
		List list = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			IPreferenceNode prefNode = getNodeExpression(prefNodes[i]);
			if (prefNode != null) {
				list.add(prefNode);
			}
		}
		return (IPreferenceNode[]) list.toArray(new IPreferenceNode[list.size()]);
	}
