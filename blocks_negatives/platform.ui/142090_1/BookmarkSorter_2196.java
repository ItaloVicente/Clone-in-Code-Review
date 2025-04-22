            return result * directions[column];
        }
        case CREATION_TIME: {
            long result;
            try {
                result = marker1.getCreationTime() - marker2.getCreationTime();
            } catch (CoreException e) {
                result = 0;
            }
            if (result == 0) {
