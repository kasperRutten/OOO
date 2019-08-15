package model.domain;

import java.util.Collection;

public interface KortingStrat {
    double applyKorting(Collection<Artikel> lijst);
}
