        if (createParents) {
            sb.append(" createParents ");
        }
        if (attributeAccess && createParents) {
            sb.append(", attributeAccess ");
        } else if (attributeAccess) {
            sb.append(" attributeAccess ");
        }
