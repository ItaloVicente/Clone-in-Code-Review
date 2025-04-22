            stringBuffer.append(id);
            stringBuffer.append(',');
            stringBuffer.append(name);
            stringBuffer.append(',');
            stringBuffer.append(description);
            stringBuffer.append(',');
            stringBuffer.append(parentId);
            stringBuffer.append(',');
            stringBuffer.append(defined);
            stringBuffer.append(')');
            string = stringBuffer.toString();
        }
        return string;
    }

    /**
     * Makes this context become undefined. This has the side effect of changing
     * the name, description and parent identifier to <code>null</code>.
     * Notification is sent to all listeners.
     */
    @Override
