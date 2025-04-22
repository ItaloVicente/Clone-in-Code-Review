    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{").append(type());
        if (createParents) {
            sb.append(", createParents");
        }
        sb.append(':').append(path()).append('}');
        return sb.toString();
    }

