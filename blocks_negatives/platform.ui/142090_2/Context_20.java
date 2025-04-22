        final Context scheme = (Context) object;
        int compareTo = Util.compare(this.id, scheme.id);
        if (compareTo == 0) {
            compareTo = Util.compare(this.name, scheme.name);
            if (compareTo == 0) {
                compareTo = Util.compare(this.parentId, scheme.parentId);
                if (compareTo == 0) {
