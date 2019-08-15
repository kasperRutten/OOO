package model.domain;

import java.util.Collection;

public interface LoadSave {
    void save();
    void load();
    Collection<Artikel> getList();
}
