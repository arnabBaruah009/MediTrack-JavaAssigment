package com.airtribe.meditrack.interfaces;

import java.util.List;

public interface Searchable<T> {
    List<T> searchByKeyword(String keyword);
}
