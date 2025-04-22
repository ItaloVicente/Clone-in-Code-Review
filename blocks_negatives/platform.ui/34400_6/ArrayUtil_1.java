        if (array == null || element == null)
            return false;
        else {
            for (int i = 0; i < array.length; i++)
                if (array[i] == element)
                    return true;
            return false;
        }
