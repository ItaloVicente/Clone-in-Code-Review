


    private ArrayList<OrganizationElement> subGroups;

    private ArrayList<OrganizationElement> users;

    private ArrayList<OrganizationElement> contents;

    /**
     * Constructor.
     * Creates a new GroupElement within the passed parent GroupElement,
     * gives it the passed name property, sets Icon.
     *
     * @param name the name
     * @param parent the parent
     */
    public GroupElement(String name, GroupElement parent) {
        super(name, parent);
    }

    /**
     * Adds an OrganizationElement to this GroupElement.
     *
     * @param userGroup The Organization Element
     */
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

    /**
     * Creates a new <code>GroupElement</code>
     * nested in this <code>GroupElement<code>
     *
     * @param name the name of the sub group
     */
    public GroupElement createSubGroup(String name) {
        GroupElement newGroup = new GroupElement(name, this);
        add(newGroup);
        return newGroup;
    }

    /**
     * Creates a new <code>UserElement</code>
     *
     * @param the name of the user element
     */
    public UserElement createUser(String name) {
        UserElement newUser = new UserElement(name, this);
        add(newUser);
        return newUser;
    }

    /**
     * Deletes an OrganizationElement from this GroupElement.
     *
     * @param userGroup the Organization Element
     */
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
