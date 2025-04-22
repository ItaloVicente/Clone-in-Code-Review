		int curWhere = where;
		where = setRelToInfo(info);

		if (where == curWhere && wasModified == isModified())
			return true;
		wasModified = isModified();

		showFeedback();
