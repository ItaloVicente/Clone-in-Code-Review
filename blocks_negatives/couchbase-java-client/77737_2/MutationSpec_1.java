        StringBuilder sb = new StringBuilder("{").append(type());
        if (createParents) {
            sb.append(", createParents");
        }
        if (attributeAccess) {
            sb.append(", attributeAccess");
        }
        sb.append(':').append(path()).append('}');
