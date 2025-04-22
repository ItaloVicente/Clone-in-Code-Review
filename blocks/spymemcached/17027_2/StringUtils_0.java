    if (!keys.isEmpty()) {
      Iterator<String> itr = keys.iterator();
      sb.append(itr.next());
      while (itr.hasNext()) {
        sb.append(delimiter);
        sb.append(itr.next());
      }
