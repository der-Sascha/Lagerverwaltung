package de.doit.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Lese-Schnittstelle (read-only) fuer ein DAO.
 *
 * <p>Definiert nur das Lesen aller Datensaetze. Reiter, die ihre Daten nur
 * anzeigen, brauchen genau diese eine Methode. Das volle CRUD steht in
 * {@link GenericDAO}, das diese Schnittstelle erweitert.</p>
 *
 * @param <T> der Modell-/Entitaetstyp, den das DAO liest (Referenztyp)
 */
public interface LeseDAO<T> {

    // === Stufe 1 — LESEN (Vertrag) ===
    /** Liest alle Datensaetze als Collection (Liste von Referenzen). */
    List<T> findAll() throws SQLException;
}
