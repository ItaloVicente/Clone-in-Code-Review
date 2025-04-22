        if (name == null) {
            throw new NullPointerException("name");
        }
        if (redactionLevel == null)  {
            throw new NullPointerException("redactionLevel");
        }
        this.name = name;
        this.redactionLevel = redactionLevel;
