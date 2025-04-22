	public static String P_USERS = "users"; //$NON-NLS-1$

	public static String P_SUBGROUPS = "subgroups"; //$NON-NLS-1$

	public static String P_CONTENTS = "contents"; //$NON-NLS-1$

	private ArrayList<OrganizationElement> subGroups;

	private ArrayList<OrganizationElement> users;

	private ArrayList<OrganizationElement> contents;

	public GroupElement(String name, GroupElement parent) {
		super(name, parent);
	}

	public void add(OrganizationElement userGroup) {
		if (userGroup.isUser() || userGroup.isGroup()) {
			getContents().add(userGroup);
		}
		if (userGroup.isGroup()) {
			getSubGroups().add(userGroup);
			userGroup.setParent(this);
		}
		if (userGroup.isUser()) {
			getUsers().add(userGroup);
			userGroup.setParent(this);
		}

	}

	public GroupElement createSubGroup(String name) {
		GroupElement newGroup = new GroupElement(name, this);
		add(newGroup);
		return newGroup;
	}

	public UserElement createUser(String name) {
		UserElement newUser = new UserElement(name, this);
		add(newUser);
		return newUser;
	}

	public void delete(OrganizationElement userGroup) {
		if (userGroup.isUser() || userGroup.isGroup()) {
			getContents().remove(userGroup);
		}
		if (userGroup.isGroup()) {
			getSubGroups().remove(userGroup);
		}
		if (userGroup.isUser()) {
			getUsers().remove(userGroup);
		}
	}

	@Override
