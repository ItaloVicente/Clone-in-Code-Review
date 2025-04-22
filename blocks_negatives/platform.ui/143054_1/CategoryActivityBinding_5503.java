        if (string == null) {
            final StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append('[');
            stringBuffer.append(activityId);
            stringBuffer.append(',');
            stringBuffer.append(categoryId);
            stringBuffer.append(']');
            string = stringBuffer.toString();
        }

        return string;
    }
