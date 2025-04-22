            if (left <= right) {
                swap(keys, left, right);
                swap(values, left, right);
                left++;
                right--;
            }
        } while (left <= right);
