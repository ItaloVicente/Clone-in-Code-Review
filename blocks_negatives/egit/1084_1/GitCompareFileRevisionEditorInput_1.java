		if(left != null && left instanceof GitResourceNode) {
			String ci = ((GitResourceNode)left).getContentIdentifier();
			if(ci != null) {
				cc.setLeftLabel(truncatedRevision(ci));
			}
		}
		if(right != null && right instanceof GitResourceNode) {
			String ci = ((GitResourceNode)right).getContentIdentifier();
			if(ci != null) {
				cc.setRightLabel(truncatedRevision(ci));
			}
		}
