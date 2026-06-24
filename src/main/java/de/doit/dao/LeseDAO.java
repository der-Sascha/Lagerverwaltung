package de.doit.dao;

import java.sql.SQLException;
import java.util.List;



public interface LeseDAO<T> {

    // hier darf kein code in {} stehen. Jeder der Zugreift muss es genau so verwenden + dann den Code {}
    // Name findAll, Rückgabe List
    // hier werden MEthoden angekündigt (erstellt ohne Code) welche benutzt werden müssen dann mit {code}
    List<T> findAll() throws SQLException;
}
