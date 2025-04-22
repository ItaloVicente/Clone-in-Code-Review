		return true;
	}

	public void setBlockedStatus(IStatus blockedStatus) {
		this.blockedStatus = blockedStatus;
	}

	void setGroupInfo(GroupInfo group) {
		parent = group;
	}

	void setTaskName(String name) {
		taskInfo.setTaskName(name);
	}

	public void setTicks(int ticks) {
		this.ticks = ticks;
	}
