    int hashCode = 0;

    if (scheme != null)
    {
      hashCode ^= scheme.toLowerCase().hashCode();
    }
    if (authority != null)
    {
      hashCode ^= authority.hashCode();
    }
    if (device != null)
    {
      hashCode ^= device.hashCode();
    }
    if (query != null)
    {
      hashCode ^= query.hashCode();
    }
    if (fragment != null)
    {
      hashCode ^= fragment.hashCode();
    }

    for (String segment : segments) {
      hashCode ^= segment.hashCode();
    }
