        if (string == null) {
            final StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append('[');
            stringBuffer.append(activityId);
            stringBuffer.append(',');
            stringBuffer.append(isEqualityPattern());
            stringBuffer.append(',');
            stringBuffer.append(getString());
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }
