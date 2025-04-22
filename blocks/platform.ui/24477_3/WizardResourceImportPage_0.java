        if (this.containerNameField != null) {
            return getPathFromText(this.containerNameField);
        }

        if (this.initialContainerFieldValue != null && this.initialContainerFieldValue.length() > 0) {
            return new Path(this.initialContainerFieldValue).makeAbsolute();
        }

        return Path.EMPTY;
