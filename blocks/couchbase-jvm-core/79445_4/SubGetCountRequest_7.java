
    public boolean accessDeleted() {
        return this.accessDeleted;
    }

    public void accessDeleted(boolean accessDeleted) {
        if (!this.xattr && accessDeleted) {
            throw new IllegalArgumentException("Invalid to access document attributes with access deleted. It can be used only with xattr.");
        }
        this.accessDeleted = accessDeleted;
    }
