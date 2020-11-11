package ar.edu.iua.model.persistence;

import ar.edu.iua.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface OrdenRepository extends JpaRepository<Orden, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE orden o SET o.caudal = ?2, o.densidad = ?3, o.temperatura = ?4, o.masa_acumulada = ?5, o.fecha_ultimo_almacenamiento = ?6 WHERE o.id = ?1", nativeQuery = true)
    void actualizarOrdenSurtidorConFecha(long idOrden, double caudal , double densidad, double temperatura, double masaAcumulada, Date fechaUltimoAlmacenamiento);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orden o SET o.caudal = ?2, o.densidad = ?3, o.temperatura = ?4, o.masa_acumulada = ?5 WHERE o.id = ?1", nativeQuery = true)
    void actualizarOrdenSurtidor(long idOrden, double caudal , double densidad, double temperatura, double masaAcumulada);
}
